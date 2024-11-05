package com.example.lbms.Service;

import com.example.lbms.Model.Book;
import com.example.lbms.Model.BookCopy;
import com.example.lbms.Repository.BookCopyRepository;
import com.example.lbms.Repository.BookRepository;
import com.example.lbms.Utils.DTO.BookCopyDTO;
import com.example.lbms.Utils.DTO.ReturnBookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookCopyService {

    private static final long DAYS_FINE_FACTOR = 5;
    private static final long RETURN_DAY_BUFFER = 7;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<BookCopy> getAllCopies(){
        return bookCopyRepository.findAll();
    }

    public boolean addBookCopy(int copies, BookCopyDTO bookCopyDTO){
        try {
            Book book = bookRepository.findById(bookCopyDTO.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            for (int i = 0; i < copies; i++) {
                BookCopy bookCopy = BookCopy.builder()
                        .borrowedByUser(null)
                        .isBorrowed(false)
                        .dateOfIssue(null)
                        .book(book)
                        .build();

                bookCopyRepository.saveAndFlush(bookCopy);
            }
            return true;
        } catch(Exception e){
            System.out.println("Book Copy Service: " + e.getMessage());
            return false;
        }
    }

    public boolean updateBookCopy(BookCopyDTO bookCopyDTO){
        return false;
    }

    public boolean deleteAllBookCopy(BookCopyDTO bookCopyDTO){
        try {
            List<BookCopy> bookCopies = bookCopyRepository.findAllByBookBookId(bookCopyDTO.getBookId());
            if(bookCopies.isEmpty()){
                 throw new RuntimeException("Provided number of book copies are not present in DB");
            }

            bookCopyRepository.deleteAll(bookCopies);
            return true;
        } catch(Exception e){
            System.out.println("Book Copy DTO: " + e.getMessage());
            return false;
        }
    }

    public boolean borrowBook(BookCopyDTO bookCopyDTO) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            BookCopy bookCopy = bookCopyRepository.findById(bookCopyDTO.getBookCopyId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            if (bookCopy.isBorrowed()) {
                throw new RuntimeException("This book copy is already borrowed.");
            }

            if (bookCopy.getBorrowedByUser() == null) {
                bookCopy.setBorrowedByUser(new ArrayList<>());
            }

            if (bookCopy.getDateOfIssue() == null) {
                bookCopy.setDateOfIssue(new ArrayList<>());
            }

            bookCopy.setBorrowed(true);
            bookCopy.getBorrowedByUser().add(userDetails.getUsername());
            bookCopy.getDateOfIssue().add(new Date());

            bookCopyRepository.saveAndFlush(bookCopy);

            return true;
        } catch(Exception e){
            System.out.println("Book Copy Service: " + e.getMessage());
            return false;
        }
    }

    public ReturnBookDTO returnBook(BookCopyDTO bookCopyDTO) {
        try {
            double fine = 0;
            BookCopy bookCopy = bookCopyRepository.findById(bookCopyDTO.getBookCopyId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            if (!bookCopy.isBorrowed()) {
                throw new RuntimeException("This book copy is not currently borrowed.");
            }

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();

            List<String> borrowedByUser = bookCopy.getBorrowedByUser();
            if (borrowedByUser == null || borrowedByUser.isEmpty()) {
                throw new RuntimeException("No users have borrowed this book copy.");
            }
            String lastBorrowedUser = borrowedByUser.get(borrowedByUser.size() - 1);
            if (!lastBorrowedUser.equals(username)) {
                throw new RuntimeException("Only the last user who borrowed this book copy can return it.");
            }

            bookCopy.setBorrowed(false);

            Date currentDate = new Date();
            LocalDate returnDate = LocalDate.now().plusDays(RETURN_DAY_BUFFER);
            Date expectedReturnDate =  Date.from(returnDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (currentDate.after(expectedReturnDate)) {
                long diffInMillies = currentDate.getTime() - expectedReturnDate.getTime();
                long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
                fine = diffInDays * DAYS_FINE_FACTOR;
            }

            if (bookCopy.getDateOfReturn() == null) {
                bookCopy.setDateOfReturn(new ArrayList<>());
            }
            bookCopy.getDateOfReturn().add(currentDate);

            bookCopyRepository.saveAndFlush(bookCopy);

            return ReturnBookDTO.builder().fine(fine).build();
        } catch (Exception e) {
            System.out.println("Book Copy Service: " + e.getMessage());
            return null;
        }
    }
}
