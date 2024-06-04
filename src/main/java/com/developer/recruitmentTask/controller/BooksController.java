package com.developer.recruitmentTask.controller;

import com.developer.recruitmentTask.dto.BooksDTO;
import com.developer.recruitmentTask.service.BooksService;
import com.developer.recruitmentTask.util.error.BadRequestAlertException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.developer.recruitmentTask.util.error.ErrorConstants.*;
import static com.developer.recruitmentTask.util.error.ErrorConstants.NOT_FOUND_KEY;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books Controller", description = "The Books API With Description Tag Annotation")
public class BooksController {

    private static final String ENTITY_NAME = "book";

    @Autowired
    private BooksService booksService;

    @Operation(summary = "Get All Books", description = "Both User ROLE_ADMIN And ROLE_USER Can Access This Endpoint")
    @GetMapping(value = "/all")
    public List<BooksDTO> allBooks() {
        return booksService.findAllBooks();
    }

    @Operation(summary = "Get Book By Book ID", description = "Both User ROLE_ADMIN And ROLE_USER Can Access This Endpoint")
    @GetMapping(value = "/find/{bookId}")
    public ResponseEntity<BooksDTO> getBookById(@PathVariable Long bookId) {
        if (bookId == null){
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        BooksDTO books = booksService.getBookById(bookId);
        if (books == null) {
            throw new BadRequestAlertException(BOOK_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Create New Book", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @PostMapping(value = "/newBook")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooksDTO> newBook(@RequestBody BooksDTO booksDTO) {
            BooksDTO createdBook = booksService.createNewBook(booksDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @Operation(summary = "Update Book With Specific Id", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @PutMapping("/updateBook/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooksDTO> updateBook(@PathVariable Long bookId, @RequestBody BooksDTO booksDTO) {
        if (bookId == null || booksDTO == null){
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        BooksDTO updatedBook = booksService.updateBook(booksDTO, bookId);
        return ResponseEntity.ok(updatedBook);
    }

    @Operation(summary = "Delete Book By Id", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @DeleteMapping(value = "/delete/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        if (bookId == null) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        booksService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }
}
