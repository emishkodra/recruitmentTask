package com.developer.recruitmentTask.controller;

import com.developer.recruitmentTask.dto.AuthorsDTO;
import com.developer.recruitmentTask.service.AuthorsService;
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

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Authors Controller", description = "The Authors API With Description Tag Annotation")
public class AuthorsController {

    private static final String ENTITY_NAME = "author";

    @Autowired
    private AuthorsService authorsService;

    @Operation(summary = "Get All Authors", description = "Both User ROLE_ADMIN And ROLE_USER Can Access This Endpoint")
    @GetMapping(value = "/all")
    public List<AuthorsDTO> allAuthors() {
        return authorsService.findAllAuthors();
    }

    @Operation(summary = "Get Author By Author ID", description = "Both User ROLE_ADMIN And ROLE_USER Can Access This Endpoint")
    @GetMapping(value = "/find/{authorId}")
    public ResponseEntity<AuthorsDTO> getAuthorById(@PathVariable Long authorId) {
        if (authorId == null){
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        AuthorsDTO authors = authorsService.getAuthorById(authorId);
        if (authors == null) {
            throw new BadRequestAlertException(AUTHOR_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        return ResponseEntity.ok(authors);
    }

    @Operation(summary = "Create New Author", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @PostMapping(value = "/newAuthor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorsDTO> newAuthor(@RequestBody AuthorsDTO authorsDTO) {
        AuthorsDTO createdAuthor = authorsService.createNewAuthor(authorsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }

    @Operation(summary = "Update Author With Specific Id", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @PutMapping("/updateAuthor/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorsDTO> updateAuthor(@PathVariable Long authorId, @RequestBody AuthorsDTO authorsDTO) {
        if (authorsDTO == null || authorId == null) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        AuthorsDTO updatedAuthor = authorsService.updateAuthor(authorsDTO, authorId);
        return ResponseEntity.ok(updatedAuthor);
    }

    @Operation(summary = "Delete Author By Id", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @DeleteMapping(value = "/delete/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId) {
        if (authorId == null) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        authorsService.deleteAuthor(authorId);
        return ResponseEntity.ok().build();
    }
}
