package com.developer.recruitmentTask.service;

import com.developer.recruitmentTask.dto.AuthorsDTO;

import java.util.List;

public interface AuthorsService {

    AuthorsDTO createNewAuthor(AuthorsDTO authorsDTO);

    AuthorsDTO updateAuthor(AuthorsDTO authorsDTO, Long authorId);

    void deleteAuthor(Long postId);

    List<AuthorsDTO> findAllAuthors();

    AuthorsDTO getAuthorById(Long authorId);
}
