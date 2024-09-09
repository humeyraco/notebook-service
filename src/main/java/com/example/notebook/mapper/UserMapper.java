package com.example.notebook.mapper;

import com.example.notebook.entity.User;
import com.example.notebook.interfaces.request.SignupRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring")
public interface UserMapper {
    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "role", expression = "java(com.example.notebook.enums.UserRole.fromRoleId(signupRequest.getRoleId()))")
    User mapToUser(SignupRequest signupRequest);

}
