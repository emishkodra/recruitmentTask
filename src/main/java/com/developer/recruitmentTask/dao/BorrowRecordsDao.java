package com.developer.recruitmentTask.dao;
import com.developer.recruitmentTask.entity.BorrowRecords;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRecordsDao extends JpaRepository<BorrowRecords, Long> {

    List<BorrowRecords> findAllByOrderByIdDesc();

    List<BorrowRecords> findByBooks_Id(Long bookId);
}