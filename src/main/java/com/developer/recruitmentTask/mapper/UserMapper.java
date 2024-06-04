package com.developer.recruitmentTask.mapper;

import com.developer.recruitmentTask.dto.UserDTO;
import com.developer.recruitmentTask.entity.User;
import com.developer.recruitmentTask.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Mapping(source="role",target = "authorities")
    UserDTO toDto(User user);

    @Mapping(source="authorities",target = "role")
    User toEntity(UserDTO userDTO);

}
