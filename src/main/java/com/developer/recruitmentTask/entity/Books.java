package com.developer.recruitmentTask.entity;

import com.developer.recruitmentTask.util.isbn.ValidISBN;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Books implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100,  nullable = false)
    private String title;

    @ValidISBN // ISBN validation
    @Column(name = "ISBN", length = 20, nullable = false)
    private String ISBN;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(name = "status")
    private Boolean status = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Authors authors;

}
