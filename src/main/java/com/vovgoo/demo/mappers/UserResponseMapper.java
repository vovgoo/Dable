package com.vovgoo.demo.mappers;

import com.vovgoo.demo.dtos.user.UserResponse;
import com.vovgoo.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "role", source = "role")
    UserResponse toDto(User user);
}
