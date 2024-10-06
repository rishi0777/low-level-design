package com.example.lbms.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@ToString
@Entity(name="BookCopy")
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookCopyId;
    private boolean isBorrowed;
    private Date dateOfIssue;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false) // foreign key
    private Book book; // reference to book entity
}
