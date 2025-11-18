package com.adil.usermanagementservice.service;

import com.adil.usermanagementservice.domain.entity.UserEntity;
import com.adil.usermanagementservice.domain.model.dto.request.UserCreateRequest;
import com.adil.usermanagementservice.domain.model.dto.request.UserUpdateRequest;
import com.adil.usermanagementservice.domain.model.dto.response.PageResponse;
import com.adil.usermanagementservice.domain.model.dto.response.UserResponse;
import com.adil.usermanagementservice.domain.repository.UserRepository;
import com.adil.usermanagementservice.exception.EmailAlreadyExistsException;
import com.adil.usermanagementservice.exception.PhoneAlreadyExistsException;
import com.adil.usermanagementservice.exception.UserNotFoundException;
import com.adil.usermanagementservice.kafka.producer.UserProducer;
import com.adil.usermanagementservice.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserProducer userProducer;
    UserMapper userMapper;

    @Transactional
    public UserResponse create(UserCreateRequest request) {
        log.info("Creating user... email={}", request.email());

        checkEmailAlreadyExists(request.email());
        checkPhoneAlreadyExists(request.phone());

        var user = userMapper.toEntity(request);

        var savedUser = userRepository.save(user);
        log.info("User saved in DB. id={}", savedUser.getId());

        var event = userMapper.toEvent(savedUser);
        userProducer.sendUserCreated(event);

        return userMapper.toResponse(savedUser);
    }

    private void checkPhoneAlreadyExists(String phone) {
        if (phone != null && userRepository.existsByPhone(phone)) {
            throw new PhoneAlreadyExistsException(phone);
        }
    }

    private void checkEmailAlreadyExists(String email) {
        if (email != null && userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    public UserResponse getById(Long id) {
        log.error("Fetching user by id={}", id);

        var user = findById(id);
        return userMapper.toResponse(user);
    }

    private UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public PageResponse<UserResponse> getAll(int pageNumber, int pageSize) {
        log.info("Fetching all users... page={}, size={}", pageNumber, pageSize);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var responses = userRepository.findAll(pageable)
                .map(userMapper::toResponse);

        log.info("Fetched {} users from page={}", responses.getContent().size(), pageNumber);

        return new PageResponse<>(
                responses.getContent(),
                pageNumber,
                pageSize,
                responses.getTotalElements(),
                responses.getTotalPages()
        );
    }

    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        log.info("Updating user id={}", id);

        var userEntity = findById(id);

        checkEmailAlreadyExists(request.email());
        checkPhoneAlreadyExists(request.phone());

        userMapper.update(userEntity, request);

        var event = userMapper.toEvent(userEntity);
        userProducer.sendUserUpdated(event);

        log.info("User updated successfully id={}", id);
        return userMapper.toResponse(userEntity);
    }

    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting user id={}", id);

        var userEntity = findById(id);

        userRepository.deleteById(id);

        var event = userMapper.toEvent(userEntity);
        userProducer.sendUserUpdated(event);

        log.info("User deleted id={}", id);
    }
}
