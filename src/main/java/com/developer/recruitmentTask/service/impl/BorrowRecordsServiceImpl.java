package com.developer.recruitmentTask.service.impl;

import com.developer.recruitmentTask.dao.BooksDao;
import com.developer.recruitmentTask.dao.BorrowRecordsDao;
import com.developer.recruitmentTask.dto.BorrowRecordsDTO;
import com.developer.recruitmentTask.entity.Books;
import com.developer.recruitmentTask.entity.BorrowRecords;
import com.developer.recruitmentTask.mapper.BooksMapper;
import com.developer.recruitmentTask.mapper.BorrowRecordsMapper;
import com.developer.recruitmentTask.service.BorrowRecordsService;
import com.developer.recruitmentTask.util.error.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.developer.recruitmentTask.util.error.ErrorConstants.*;
import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
public class BorrowRecordsServiceImpl implements BorrowRecordsService {

    @Autowired
    private BorrowRecordsDao borrowRecordsDao;

    @Autowired
    private BorrowRecordsMapper borrowRecordsMapper;

    @Autowired
    private BooksDao booksDao;

    @Autowired
    private BooksMapper booksMapper;


    @Override
    public BorrowRecordsDTO createBorrowRecords(BorrowRecordsDTO borrowRecordsDTO) {

        // validate that the borrowDate is before the returnDate
        if (borrowRecordsDTO.getReturnDate() != null && !borrowRecordsDTO.getReturnDate().isAfter(borrowRecordsDTO.getBorrowDate())) {
            throw new BadRequestAlertException(RETURN_DATE_AFTER_BORROW_DATE_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
        }

        // retrieve book using the ID provided within the BorrowRecordsDTO.
        Optional<Books> book = booksDao.findById(borrowRecordsDTO.getBooksDTO().getId());

        if(book.isEmpty()){
            throw new BadRequestAlertException(BOOK_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }

        // update BooksDTO inside BorrowRecordsDTO
        borrowRecordsDTO.setBooksDTO(booksMapper.toDto(book.get()));

        // convert BorrowRecordsDTO to BorrowRecords using mapper
        BorrowRecords borrowRecords = borrowRecordsMapper.toEntity(borrowRecordsDTO);
        borrowRecords = borrowRecordsDao.save(borrowRecords);
        return borrowRecordsMapper.toDto(borrowRecords);
    }

    @Override
    public List<BorrowRecordsDTO> findAllBorrowRecords(){
        List<BorrowRecords> borrowRecords = borrowRecordsDao.findAllByOrderByIdDesc(); // get all borrowRecords ordered by their ID in descending order
        return borrowRecordsMapper.toDto(borrowRecords);
    }

    @Override
    public BorrowRecordsDTO getBorrowRecordsById(Long borrowRecordId){
        // check if the provided borrowRecordId is null or 0
        if (borrowRecordId == null || borrowRecordId == 0) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        // attempt to find the existing borrow record in the db with the specified ID
        Optional<BorrowRecords> borrowRecords = borrowRecordsDao.findById(borrowRecordId);

        // map Optional<BorrowRecords> to BorrowRecordsDTO using a mapper
        return borrowRecordsMapper.toDto(borrowRecords.orElseThrow(() ->
                new BadRequestAlertException(BORROW_RECORD_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY)));  // if borrow records not found -> throw an exception.
    }

    @Override
    public BorrowRecordsDTO updateBorrowRecords(BorrowRecordsDTO borrowRecordsDTO, Long borrowRecordId){
        // check for existingBorrowRecords or 0
        if (borrowRecordId == null || borrowRecordId == 0){
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
        }
        // attempt to find the existing borrow records in the db with the specified ID
        Optional<BorrowRecords> existingBorrowRecords = borrowRecordsDao.findById(borrowRecordId);
        if (existingBorrowRecords.isEmpty()) {
            throw new BadRequestAlertException(BORROW_RECORD_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }

        // validate that borrowDate is still before returnDate
        if (borrowRecordsDTO.getReturnDate() != null && !borrowRecordsDTO.getReturnDate().isAfter(borrowRecordsDTO.getBorrowDate())) {
            throw new BadRequestAlertException(RETURN_DATE_AFTER_BORROW_DATE_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
        }

        // get borrow records entity from Optional and update it
        BorrowRecords borrowRecordsToUpdate = existingBorrowRecords.get();
        borrowRecordsToUpdate.setBorrowerName(borrowRecordsDTO.getBorrowerName());
        borrowRecordsToUpdate.setBorrowDate(borrowRecordsDTO.getBorrowDate());
        borrowRecordsToUpdate.setReturnDate(borrowRecordsDTO.getReturnDate());

        // check and update the borrow records associated with the book
        if (borrowRecordsDTO.getBooksDTO() != null && borrowRecordsDTO.getBooksDTO().getId() != null) {

            Optional<Books> bookId = booksDao.findById(borrowRecordsDTO.getBooksDTO().getId());

            if (bookId.isPresent()) {
                Books bookToUpdate = bookId.get();
                // updated book and set it back to borrow records
                borrowRecordsToUpdate.setBooks(bookToUpdate);
            } else {
                throw new BadRequestAlertException(BOOK_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
            }
        } else {
            throw new BadRequestAlertException(INVALID_BOOK_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
        }
        // save the updated book and convert it back to DTO
        borrowRecordsDao.save(borrowRecordsToUpdate);
        return borrowRecordsMapper.toDto(borrowRecordsToUpdate);
    }

    @Override
    public void deleteBorrowRecords(Long borrowRecordId) {
        // check if the provided borrowRecordId is not null or 0
        if (borrowRecordId == null || borrowRecordId == 0 ) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
            }
            // check if the borrow record exists in the db
            Optional<BorrowRecords> borrowRecord = borrowRecordsDao.findById(borrowRecordId);

            if (borrowRecord.isEmpty()) {
                throw new BadRequestAlertException(BORROW_RECORD_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
            }
            // check for existing borrow record associated with the selected book
            if (borrowRecord.get().getBooks() != null) {
                throw new BadRequestAlertException(BORROW_RECORD_ASSOCIATED_WITH_BOOK_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
            }
            // if no borrow records are found delete the book from db
            booksDao.deleteById(borrowRecordId);
        }
}
