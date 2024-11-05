package com.example.lbms.Utils.DTO;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
public class BookCopyDTO {
    private Long bookCopyId;
    private Long bookId;
    private String borrowedByUser;
}
