package com.developer.recruitmentTask.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BorrowRecordsDTO {
    private Long id;
    private String borrowerName;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    @JsonIgnore
    private Boolean status;
    private BooksDTO booksDTO;
}
