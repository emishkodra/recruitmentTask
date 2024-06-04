package com.developer.recruitmentTask.controller;

import com.developer.recruitmentTask.dto.BorrowRecordsDTO;
import com.developer.recruitmentTask.service.BorrowRecordsService;
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
@RequestMapping("/api/borrowRecords")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Borrow Records Controller", description = "The Borrow Records API With Description Tag Annotation")
public class BorrowRecordsController {

    private static final String ENTITY_NAME = "borrowRecords";

    @Autowired
    private BorrowRecordsService borrowRecordsService;

    @Operation(summary = "Get All Borrow Records", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @GetMapping(value = "/all")
    public List<BorrowRecordsDTO> allBorrowRecords() {
        return borrowRecordsService.findAllBorrowRecords();
    }

    @Operation(summary = "Get Borrow Record By Borrow Record ID", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @GetMapping(value = "/find/{borrowRecordId}")
    public ResponseEntity<BorrowRecordsDTO> getBorrowRecordId(@PathVariable Long borrowRecordId) {
        if (borrowRecordId == null){
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        BorrowRecordsDTO borrowRecords = borrowRecordsService.getBorrowRecordsById(borrowRecordId);
        if (borrowRecords == null) {
            throw new BadRequestAlertException(BORROW_RECORD_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        return ResponseEntity.ok(borrowRecords);
    }

    @Operation(summary = "Create New Borrow Record", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @PostMapping(value = "/newBorrowRecord")
    public ResponseEntity<BorrowRecordsDTO> newBorrowRecord(@RequestBody BorrowRecordsDTO borrowRecordsDTO) {
        BorrowRecordsDTO createdBorrowRecords = borrowRecordsService.createBorrowRecords(borrowRecordsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBorrowRecords);
    }

    @Operation(summary = "Update Borrow Record With Specific Id", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @PutMapping("/updateBorrowRecord/{borrowRecordId}")
    public ResponseEntity<BorrowRecordsDTO> updateBorrowRecord(@PathVariable Long borrowRecordId, @RequestBody BorrowRecordsDTO borrowRecordsDTO) {
        if (borrowRecordId == null || borrowRecordsDTO == null){
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        BorrowRecordsDTO updatedBorrowRecord = borrowRecordsService.updateBorrowRecords(borrowRecordsDTO, borrowRecordId);
        return ResponseEntity.ok(updatedBorrowRecord);
    }

    @Operation(summary = "Delete Borrow Record By Id", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @DeleteMapping(value = "/delete/{borrowRecordId}")
    public ResponseEntity<Void> deleteBorrowRecords(@PathVariable Long borrowRecordId) {
        if (borrowRecordId == null) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        borrowRecordsService.deleteBorrowRecords(borrowRecordId);
        return ResponseEntity.ok().build();
    }

}
