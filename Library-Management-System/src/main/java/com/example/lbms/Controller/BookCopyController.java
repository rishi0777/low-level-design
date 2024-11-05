package com.example.lbms.Controller;

import com.example.lbms.Model.BookCopy;
import com.example.lbms.Service.BookCopyService;
import com.example.lbms.Utils.DTO.BookCopyDTO;
import com.example.lbms.Utils.DTO.ReturnBookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-copy")
public class BookCopyController {

    @Autowired
    private BookCopyService bookCopyService;

    @GetMapping("/get-all")
    public List<BookCopy> getAllBookCopy(){
        return bookCopyService.getAllCopies();
    }

    @PostMapping("/add")
    public boolean addCopy(@RequestParam int copies, @RequestBody BookCopyDTO bookCopyDTO){
        return bookCopyService.addBookCopy(copies,bookCopyDTO);
    }

    @PostMapping("/update")
    public boolean updateBookCopy(@RequestBody BookCopyDTO bookCopyDTO){
        return bookCopyService.updateBookCopy(bookCopyDTO);
    }

    @PostMapping("/delete")
    public boolean deleteAllCopy(@RequestBody BookCopyDTO bookCopyDTO){
        return bookCopyService.deleteAllBookCopy(bookCopyDTO);
    }

    @PostMapping("/borrow-book")
    public boolean borrowBook(@RequestBody BookCopyDTO bookCopyDTO){
        return bookCopyService.borrowBook(bookCopyDTO);
    }

    @PostMapping("/return-book")
    public ReturnBookDTO returnBook(@RequestBody BookCopyDTO bookCopyDTO){
        return bookCopyService.returnBook(bookCopyDTO);
    }
}
