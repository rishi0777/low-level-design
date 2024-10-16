package com.example.lbms.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "BookCopy")
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookCopyId;
    private boolean isBorrowed;
    private List<Date> dateOfIssue;
    private List<Date> dateOfReturn;

    // TODO: this needs to be the value as present in User table not other value
    private List<String> borrowedByUser;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false) // foreign key
    private Book book;
}
