package com.adil.usermanagementservice.mapper;

import com.adil.usermanagementservice.domain.entity.UserEntity;
import com.adil.usermanagementservice.domain.model.dto.request.UserCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserEntity toEntity(UserCreateRequest request);
}
