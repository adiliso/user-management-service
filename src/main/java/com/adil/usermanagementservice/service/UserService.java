package com.adil.usermanagementservice.service;

import com.adil.usermanagementservice.domain.entity.UserEntity;
import com.adil.usermanagementservice.domain.model.dto.request.UserCreateRequest;
import com.adil.usermanagementservice.domain.model.dto.response.PageResponse;
import com.adil.usermanagementservice.domain.model.dto.response.UserResponse;
import com.adil.usermanagementservice.domain.repository.UserRepository;
import com.adil.usermanagementservice.exception.EmailAlreadyExistsException;
import com.adil.usermanagementservice.exception.PhoneAlreadyExistsException;
import com.adil.usermanagementservice.exception.UserNotFoundException;
import com.adil.usermanagementservice.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    @Transactional
    public Long create(UserCreateRequest request) {
        checkEmailAlreadyExists(request.email());
        checkPhoneAlreadyExists(request.phone());

        var user = userMapper.toEntity(request);

        var savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    private void checkPhoneAlreadyExists(String phone) {
        if (userRepository.existsByPhone(phone)) {
            throw new PhoneAlreadyExistsException(phone);
        }
    }

    private void checkEmailAlreadyExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    public UserResponse getById(Long id) {
        var user = findById(id);

        return userMapper.toResponse(user);
    }

    private UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public PageResponse<UserResponse> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var responses = userRepository.findAll(pageable)
                .map(userMapper::toResponse);

        return new PageResponse<>(
                responses.getContent(),
                pageNumber,
                pageSize,
                responses.getTotalElements(),
                responses.getTotalPages());
    }
}
