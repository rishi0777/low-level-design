package com.example.lbms.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String bookName;
    private String author;
    private String edition;

    @JsonIgnore // bi-directional relationship is there between book and book-copy if I do not use this then in get-all books it will result in infinite nesting
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookCopy> bookCopies;
}
