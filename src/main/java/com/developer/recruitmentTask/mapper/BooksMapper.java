package com.developer.recruitmentTask.mapper;

import com.developer.recruitmentTask.dto.BooksDTO;
import com.developer.recruitmentTask.entity.Books;
import com.developer.recruitmentTask.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {})
public interface BooksMapper extends EntityMapper<BooksDTO, Books> {

    @Mapping(source = "status", target = "status", defaultValue = "true")
    @Mapping(source = "ISBN", target = "ISBN")
    @Mapping(source = "authors", target = "authorsDTO")
    BooksDTO toDto(Books books);

    @Mapping(source = "status", target = "status", defaultValue = "true")
    @Mapping(source = "ISBN", target = "ISBN")
    @Mapping(source = "authorsDTO", target = "authors")
    Books toEntity(BooksDTO booksDTO);

    void updateFromBooks(Books books, @MappingTarget BooksDTO booksDTO);

}