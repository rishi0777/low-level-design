package com.example.lbms.Utils.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class BookDTO {
    private String bookName;
    private String author;
    private String edition;
}
