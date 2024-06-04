package com.developer.recruitmentTask.dao;

import com.developer.recruitmentTask.entity.Authors;
import com.developer.recruitmentTask.util.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorsDao extends SoftDeleteRepository<Authors, Long> {

    List<Authors> findAllByOrderByIdDesc();
}
