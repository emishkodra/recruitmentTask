package com.developer.recruitmentTask.mapper;

import com.developer.recruitmentTask.dto.BorrowRecordsDTO;
import com.developer.recruitmentTask.entity.BorrowRecords;
import com.developer.recruitmentTask.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface BorrowRecordsMapper extends EntityMapper<BorrowRecordsDTO, BorrowRecords> {

    @Mapping(source = "status", target = "status", defaultValue = "true")
    @Mapping(source = "books", target = "booksDTO")
    BorrowRecordsDTO toDto(BorrowRecords borrowRecords);

    @Mapping(source = "status", target = "status", defaultValue = "true")
    @Mapping(source = "booksDTO", target = "books")
    BorrowRecords toEntity(BorrowRecordsDTO borrowRecordsDTO);

}
