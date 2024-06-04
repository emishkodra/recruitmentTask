package com.developer.recruitmentTask.service;

import com.developer.recruitmentTask.dto.BorrowRecordsDTO;

import java.util.List;

public interface BorrowRecordsService {


    BorrowRecordsDTO createBorrowRecords(BorrowRecordsDTO borrowRecordsDTO);

    List<BorrowRecordsDTO> findAllBorrowRecords();

    BorrowRecordsDTO getBorrowRecordsById(Long borrowRecordId);

    BorrowRecordsDTO updateBorrowRecords(BorrowRecordsDTO borrowRecordsDTO, Long borrowRecordId);

    void deleteBorrowRecords(Long borrowRecordId);
}
