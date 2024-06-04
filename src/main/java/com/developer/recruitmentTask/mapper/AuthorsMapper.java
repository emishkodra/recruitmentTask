package com.developer.recruitmentTask.mapper;

import com.developer.recruitmentTask.dto.AuthorsDTO;
import com.developer.recruitmentTask.entity.Authors;
import com.developer.recruitmentTask.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {})
public interface AuthorsMapper extends EntityMapper<AuthorsDTO, Authors> {

    @Mapping(source = "status", target = "status", defaultValue = "true")
    AuthorsDTO toDto(Authors authors);

    @Mapping(source = "status", target = "status", defaultValue = "true")
    Authors toEntity(AuthorsDTO authorsDTO);

    void updateFromAuthors(Authors authors, @MappingTarget AuthorsDTO authorsDTO);

}
