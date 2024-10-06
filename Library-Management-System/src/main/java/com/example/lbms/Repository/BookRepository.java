package com.example.lbms.Repository;

import com.example.lbms.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Book findByBookName(String bookName);
    List<Book> findAllByBookNameStartingWith(String startPattern);
}
