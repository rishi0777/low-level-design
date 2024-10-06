package com.example.lbms.Service;

import com.example.lbms.Model.Book;
import com.example.lbms.Model.User.UserPrincipal;
import com.example.lbms.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public List<Book> getBooksByBookName(String bookName){
        return bookRepository.findAllByBookNameStartingWith(bookName);
    }

    public boolean insertBook(Book book){
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            boolean isAdmin = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList()
                    .contains("ADMIN");

            if (!isAdmin) {
                throw new SecurityException("Unauthorized: User does not have permission to perform this action.");
            }

            if (book == null) {
                throw new IllegalArgumentException("Book cannot be null");
            }

            bookRepository.saveAndFlush(book);
            return true;
        } catch (Exception e) {
            System.err.println("Error inserting book: " + e.getMessage());
            return false;
        }
    }

    public boolean insertAllBooks(List<Book> books){
        try {
            if (books == null) {
                throw new IllegalArgumentException("Books cannot be null");
            }

            bookRepository.saveAllAndFlush(books);
            return true;
        } catch (Exception e) {
            System.err.println("Error inserting multiple books: " + e.getMessage());
            return false;
        }
    }
    public boolean deleteBook(String bookName){
        try {
            Book book = bookRepository.findByBookName(bookName);
            if (book == null) {
                throw new IllegalArgumentException("Book not found");
            }

            bookRepository.deleteById(book.getBookId());
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting book: " + e.getMessage());
            return false;
        }
    }
}
