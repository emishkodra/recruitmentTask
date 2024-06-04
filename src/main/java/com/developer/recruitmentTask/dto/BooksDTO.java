package com.developer.recruitmentTask.dto;

import com.developer.recruitmentTask.util.isbn.ValidISBN;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BooksDTO {
    private Long id;
    private String title;
    @ValidISBN
    private String ISBN;
    private LocalDate publishedDate;
    @JsonIgnore
    private Boolean status;
    private AuthorsDTO authorsDTO;
}
