package com.developer.recruitmentTask.service;

import com.developer.recruitmentTask.dto.BooksDTO;

import java.util.List;

public interface BooksService {

    BooksDTO createNewBook(BooksDTO booksDTO);

    List<BooksDTO> findAllBooks();

    BooksDTO getBookById(Long bookId);

    BooksDTO updateBook(BooksDTO booksDTO, Long bookId);

    void deleteBook(Long bookId);
}
