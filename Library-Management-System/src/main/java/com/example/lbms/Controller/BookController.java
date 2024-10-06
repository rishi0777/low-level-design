package com.example.lbms.Controller;

import com.example.lbms.Model.Book;
import com.example.lbms.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookservice;

    @GetMapping("/getAll")
    public List<Book> getBooks(){
        return bookservice.getAllBooks();
    }

    @GetMapping("/find/{bookName}")
    public List<Book> findByBookName(@PathVariable String bookName){
        return bookservice.getBooksByBookName(bookName);
    }

    @PostMapping("/insert")
    public boolean insertBook(@RequestBody Book book){
        return bookservice.insertBook(book);
    }

    @PostMapping("/insertAll")
    public boolean insertAllBooks(@RequestBody List<Book> books){
        return bookservice.insertAllBooks(books);
    }

    @DeleteMapping("/delete/{bookName}")
    public boolean deleteBook(@PathVariable String bookName){
        return bookservice.deleteBook(bookName);
    }
}
