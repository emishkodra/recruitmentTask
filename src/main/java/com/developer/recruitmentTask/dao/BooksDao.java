package com.developer.recruitmentTask.dao;

import com.developer.recruitmentTask.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksDao extends JpaRepository<Books, Long> {

    List<Books> findAllByOrderByIdDesc();

}
