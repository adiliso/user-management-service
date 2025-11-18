package com.adil.usermanagementservice.mapper;

import com.adil.usermanagementservice.domain.entity.UserEntity;
import com.adil.usermanagementservice.domain.model.dto.request.UserCreateRequest;
import com.adil.usermanagementservice.domain.model.dto.request.UserUpdateRequest;
import com.adil.usermanagementservice.domain.model.dto.response.UserResponse;
import com.adil.usermanagementservice.kafka.event.UserEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserEntity toEntity(UserCreateRequest request);

    UserResponse toResponse(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void update(@MappingTarget UserEntity userEntity, UserUpdateRequest request);

    @Mapping(target = "timestamp", ignore = true)
    @Mapping(target = "role", ignore = true)
    UserEvent toEvent(UserEntity savedUser);
}
