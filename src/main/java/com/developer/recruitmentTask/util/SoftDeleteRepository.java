package com.developer.recruitmentTask.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SoftDeleteRepository<T, Long> extends JpaRepository<T, Long> {

    @Override
    @Modifying
//    @Query("update #{#entityName} e set e.status = false where e.id=?1")
    void deleteById(Long aLong);

}