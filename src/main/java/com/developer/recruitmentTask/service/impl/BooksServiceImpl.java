package com.developer.recruitmentTask.service.impl;

import com.developer.recruitmentTask.dao.AuthorsDao;
import com.developer.recruitmentTask.dao.BooksDao;
import com.developer.recruitmentTask.dao.BorrowRecordsDao;
import com.developer.recruitmentTask.dto.BooksDTO;
import com.developer.recruitmentTask.entity.Authors;
import com.developer.recruitmentTask.entity.Books;
import com.developer.recruitmentTask.entity.BorrowRecords;
import com.developer.recruitmentTask.mapper.AuthorsMapper;
import com.developer.recruitmentTask.mapper.BooksMapper;
import com.developer.recruitmentTask.service.BooksService;
import com.developer.recruitmentTask.util.error.BadRequestAlertException;
import com.developer.recruitmentTask.util.isbn.ISBNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.developer.recruitmentTask.util.error.ErrorConstants.*;
import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
public class BooksServiceImpl implements BooksService {

    @Autowired
    private BooksDao booksDao;

    @Autowired
    private BooksMapper booksMapper;

    @Autowired
    private AuthorsMapper authorsMapper;

    @Autowired
    private AuthorsDao authorsDao;

    @Autowired
    private BorrowRecordsDao borrowRecordsDao;

    @Override
    public BooksDTO createNewBook(BooksDTO booksDTO){
        // retrieve author using the ID provided within the BooksDTO.
        Optional<Authors> author = authorsDao.findById(booksDTO.getAuthorsDTO().getId());

        if(author.isEmpty()){
            throw new BadRequestAlertException(AUTHOR_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }

        validateISBN(booksDTO.getISBN()); // check ISBN format

        // update AuthorsDTO inside BooksDTO
        booksDTO.setAuthorsDTO(authorsMapper.toDto(author.get()));

        Books books = booksMapper.toEntity(booksDTO);// convert BooksDTO to Books using mapper
        books = booksDao.save(books);
        return booksMapper.toDto(books);
    }

    @Override
    public List<BooksDTO> findAllBooks(){
        List<Books> books = booksDao.findAllByOrderByIdDesc(); // get all books ordered by their ID in descending order
        return booksMapper.toDto(books);
    }

    @Override
    public BooksDTO getBookById(Long bookId){
        // check if the provided booksId is null or 0
        if (bookId == null || bookId == 0) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        // attempt to find the existing book in the db with the specified ID
        Optional<Books> book = booksDao.findById(bookId);

        // map Optional<Books> to BooksDTO using a mapper
        return booksMapper.toDto(book.orElseThrow(() ->
                new BadRequestAlertException(BOOK_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY)));  // if book not found -> throw an exception.
    }


    @Override
    public BooksDTO updateBook(BooksDTO booksDTO, Long bookId){
        // check for existingBooks
        if (bookId == null || bookId == 0){
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
        }

        validateISBN(booksDTO.getISBN()); // check ISBN format

        // attempt to find the existing book in the db with the specified ID
        Optional<Books> existingBooks = booksDao.findById(bookId);
        if (existingBooks.isEmpty()) {
            throw new BadRequestAlertException(BOOK_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        // get book entity from Optional and update it
        Books bookToUpdate = existingBooks.get();
        bookToUpdate.setTitle(booksDTO.getTitle());
        bookToUpdate.setISBN(booksDTO.getISBN());
        bookToUpdate.setPublishedDate(booksDTO.getPublishedDate());

        // check and update the author associated with the book
        if (booksDTO.getAuthorsDTO() != null && booksDTO.getAuthorsDTO().getId() != null) {

            Optional<Authors> authorId = authorsDao.findById(booksDTO.getAuthorsDTO().getId());

            if (authorId.isPresent()) {
                Authors authorToUpdate = authorId.get();
                bookToUpdate.setAuthors(authorToUpdate);
            } else {
                throw new BadRequestAlertException(AUTHOR_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
            }
        } else {
            throw new BadRequestAlertException(INVALID_AUTHOR_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
        }
        // save the updated book and convert it back to DTO
        booksDao.save(bookToUpdate);
        return booksMapper.toDto(bookToUpdate);
    }


    @Override
    public void deleteBook(Long bookId) {
        // check if the provided bookId is not null or 0
        if (bookId == null || bookId == 0) {
                throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
            }

            // check if the book exists in the db
            boolean bookExists = booksDao.existsById(bookId);
            if (!bookExists) {
                throw new BadRequestAlertException(BOOK_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
            }

            // check for existing BorrowRecord associated with the selected book
            List<BorrowRecords> borrowRecords = borrowRecordsDao.findByBooks_Id(bookId);
            if (!borrowRecords.isEmpty()) {
                throw new BadRequestAlertException(BOOK_ASSOCIATED_WITH_BORROW_RECORD, ENTITY_NAME, BAD_REQUEST_KEY);
            }
            // if no borrow records are found delete the book from db
            booksDao.deleteById(bookId);
    }


    // check if ISBN format is valid if not -> throw exception
    private void validateISBN(String isbn) {
        ISBNValidator validator = new ISBNValidator();
        if (!validator.isValid(isbn, null)) {
            throw new BadRequestAlertException(INVALID_ISBN_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
        }
    }
}
