package com.developer.recruitmentTask.service.impl;

import com.developer.recruitmentTask.dao.AuthorsDao;
import com.developer.recruitmentTask.dto.AuthorsDTO;
import com.developer.recruitmentTask.entity.Authors;
import com.developer.recruitmentTask.mapper.AuthorsMapper;
import com.developer.recruitmentTask.service.AuthorsService;
import com.developer.recruitmentTask.util.error.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.developer.recruitmentTask.util.error.ErrorConstants.*;
import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
public class AuthorsServiceImpl implements AuthorsService {

    @Autowired
    private AuthorsDao authorsDao;

    @Autowired
    private AuthorsMapper authorsMapper;

    @Override
    public AuthorsDTO createNewAuthor(AuthorsDTO authorsDTO){
        Authors authors = authorsMapper.toEntity(authorsDTO); // convert AuthorsDTO to Authors using mapper
        authors = authorsDao.save(authors);
        return authorsMapper.toDto(authors);
    }

    @Override
    public List<AuthorsDTO> findAllAuthors(){
        List<Authors> authors = authorsDao.findAllByOrderByIdDesc(); // get all authors ordered by their ID in descending order
        return authorsMapper.toDto(authors);
    }

    @Override
    public AuthorsDTO getAuthorById(Long authorId){
        // check if the provided authorId is null or 0
        if (authorId == null || authorId == 0) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        // attempt to find the existing author in the db with the specified ID
        Optional<Authors> author = authorsDao.findById(authorId);

        // map Optional<Authors> to AuthorsDTO using a mapper
        return authorsMapper.toDto(author.orElseThrow(() ->
                new BadRequestAlertException(AUTHOR_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY)));  // if author not found -> throw an exception.
    }

    @Override
    public AuthorsDTO updateAuthor(AuthorsDTO authorsDTO, Long authorId){
        // check for authorId
        if (authorId == null || authorId == 0) {
            throw new BadRequestAlertException(CONFLICT_ID_NOT_FOUND_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
        }

        // attempt to find the existing author in the db with the specified ID
        Authors existingAuthor = authorsDao.findById(authorId).orElseThrow(()->
                new BadRequestAlertException(AUTHOR_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY));

        // get author entity and update it
        existingAuthor.setFirstName(authorsDTO.getFirstName());
        existingAuthor.setLastName(authorsDTO.getLastName());

        // save the updated author and convert it back to DTO
        authorsDao.save(existingAuthor);
        return authorsMapper.toDto(existingAuthor);
    }

    @Override
    public void deleteAuthor(Long authorId) {
        // check if the provided authorId is not null or 0
        if (authorId == null || authorId == 0) {
                throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
            }
            // check if the author exists in the db
            boolean exists = authorsDao.existsById(authorId);
            if (!exists) {
                throw new BadRequestAlertException(AUTHOR_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
            }
            // if it exists completely delete it from the db
            authorsDao.deleteById(authorId);
    }
}
