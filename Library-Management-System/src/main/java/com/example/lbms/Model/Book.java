package com.example.lbms.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Entity(name="Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String bookName;
    private String author;
    private String edition;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookCopy> bookCopies;  // This will hold the associated BookCopy entities
}
