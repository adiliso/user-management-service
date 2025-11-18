package com.adil.usermanagementservice.service;

import com.adil.usermanagementservice.domain.entity.UserEntity;
import com.adil.usermanagementservice.domain.repository.UserRepository;
import com.adil.usermanagementservice.exception.EmailAlreadyExistsException;
import com.adil.usermanagementservice.exception.PhoneAlreadyExistsException;
import com.adil.usermanagementservice.exception.UserNotFoundException;
import com.adil.usermanagementservice.kafka.event.UserEvent;
import com.adil.usermanagementservice.kafka.producer.UserProducer;
import com.adil.usermanagementservice.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.adil.usermanagementservice.common.UserTestConstant.EMAIL;
import static com.adil.usermanagementservice.common.UserTestConstant.PHONE;
import static com.adil.usermanagementservice.common.UserTestConstant.USER_ID;
import static com.adil.usermanagementservice.common.UserTestConstant.getUserCreateRequest;
import static com.adil.usermanagementservice.common.UserTestConstant.getUserEntity;
import static com.adil.usermanagementservice.common.UserTestConstant.getUserResponse;
import static com.adil.usermanagementservice.common.UserTestConstant.getUserUpdateRequest;
import static com.adil.usermanagementservice.domain.model.enums.ErrorCode.ALREADY_EXISTS;
import static com.adil.usermanagementservice.domain.model.enums.ErrorCode.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Spy
    private UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProducer userProducer;

    @InjectMocks
    private UserService userService;

    @Test
    void create_Should_Return_Success() {
        given(userRepository.existsByEmail(EMAIL)).willReturn(false);
        given(userRepository.existsByPhone(PHONE)).willReturn(false);
        given(userRepository.save(any(UserEntity.class))).willReturn(getUserEntity());
        doNothing().when(userProducer).sendUserCreated(any(UserEvent.class));

        var userResponse = userService.create(getUserCreateRequest());

        assertNotNull(userResponse);
        assertEquals(getUserResponse(), userResponse);

        then(userRepository).should(times(1)).existsByEmail(EMAIL);
        then(userRepository).should(times(1)).existsByPhone(PHONE);
        then(userRepository).should(times(1)).save(any(UserEntity.class));
        then(userProducer).should(times(1)).sendUserCreated(any(UserEvent.class));
    }

    @Test
    void create_Should_Throw_Exception_When_Email_Exists() {
        given(userRepository.existsByEmail(EMAIL)).willReturn(true);

        var ex = assertThrows(EmailAlreadyExistsException.class,
                () -> userService.create(getUserCreateRequest()));

        assertEquals(ALREADY_EXISTS, ex.getCode());
        assertEquals("Email already exists: " + EMAIL, ex.getMessage());

        then(userRepository).should(times(1)).existsByEmail(EMAIL);
        then(userRepository).should(times(0)).existsByPhone(PHONE);
        then(userRepository).should(times(0)).save(any(UserEntity.class));
        then(userProducer).should(times(0)).sendUserCreated(any(UserEvent.class));
    }

    @Test
    void create_Should_Throw_Exception_When_Phone_Exists() {
        given(userRepository.existsByEmail(EMAIL)).willReturn(false);
        given(userRepository.existsByPhone(PHONE)).willReturn(true);

        var ex = assertThrows(PhoneAlreadyExistsException.class,
                () -> userService.create(getUserCreateRequest()));

        assertEquals(ALREADY_EXISTS, ex.getCode());
        assertEquals("Phone already exists: " + PHONE, ex.getMessage());

        then(userRepository).should(times(1)).existsByEmail(EMAIL);
        then(userRepository).should(times(1)).existsByPhone(PHONE);
        then(userRepository).should(times(0)).save(any(UserEntity.class));
        then(userProducer).should(times(0)).sendUserCreated(any(UserEvent.class));
    }

    @Test
    void update_Should_Return_Success() {
        given(userRepository.findById(USER_ID)).willReturn(Optional.of(getUserEntity()));
        given(userRepository.existsByEmail(EMAIL)).willReturn(false);
        given(userRepository.existsByPhone(PHONE)).willReturn(false);
        doNothing().when(userProducer).sendUserUpdated(any(UserEvent.class));

        var userResponse = userService.update(USER_ID, getUserUpdateRequest());

        assertNotNull(userResponse);
        assertEquals(getUserResponse(), userResponse);

        then(userRepository).should(times(1)).findById(USER_ID);
        then(userRepository).should(times(1)).existsByEmail(EMAIL);
        then(userRepository).should(times(1)).existsByPhone(PHONE);
        then(userProducer).should(times(1)).sendUserUpdated(any(UserEvent.class));
    }

    @Test
    void update_Should_Throw_Exception_When_UserNotFound() {
        given(userRepository.findById(USER_ID)).willReturn(Optional.empty());

        var ex = assertThrows(UserNotFoundException.class,
                () -> userService.update(USER_ID, getUserUpdateRequest()));

        assertEquals(USER_NOT_FOUND, ex.getCode());
        assertEquals(String.format("User with id %d was not found", USER_ID), ex.getMessage());

        then(userRepository).should(times(1)).findById(USER_ID);
        then(userRepository).should(times(0)).existsByEmail(EMAIL);
        then(userRepository).should(times(0)).existsByPhone(PHONE);
        then(userProducer).should(times(0)).sendUserUpdated(any(UserEvent.class));
    }

    @Test
    void update_Should_Throw_Exception_When_Email_Exists() {
        given(userRepository.findById(USER_ID)).willReturn(Optional.of(getUserEntity()));
        given(userRepository.existsByEmail(EMAIL)).willReturn(true);

        var ex = assertThrows(EmailAlreadyExistsException.class,
                () -> userService.update(USER_ID, getUserUpdateRequest()));

        assertEquals(ALREADY_EXISTS, ex.getCode());
        assertEquals("Email already exists: " + EMAIL, ex.getMessage());

        then(userRepository).should(times(1)).findById(USER_ID);
        then(userRepository).should(times(1)).existsByEmail(EMAIL);
        then(userRepository).should(times(0)).existsByPhone(PHONE);
        then(userProducer).should(times(0)).sendUserUpdated(any(UserEvent.class));
    }

    @Test
    void update_Should_Throw_Exception_When_Phone_Exists() {
        given(userRepository.findById(USER_ID)).willReturn(Optional.of(getUserEntity()));
        given(userRepository.existsByEmail(EMAIL)).willReturn(false);
        given(userRepository.existsByPhone(PHONE)).willReturn(true);

        var ex = assertThrows(PhoneAlreadyExistsException.class,
                () -> userService.update(USER_ID, getUserUpdateRequest()));

        assertEquals(ALREADY_EXISTS, ex.getCode());
        assertEquals("Phone already exists: " + PHONE, ex.getMessage());

        then(userRepository).should(times(1)).findById(USER_ID);
        then(userRepository).should(times(1)).existsByEmail(EMAIL);
        then(userRepository).should(times(1)).existsByPhone(PHONE);
        then(userProducer).should(times(0)).sendUserUpdated(any(UserEvent.class));
    }

    @Test
    void getById_Should_Return_Success() {
        given(userRepository.findById(USER_ID)).willReturn(Optional.of(getUserEntity()));

        var userResponse = userService.getById(USER_ID);

        assertNotNull(userResponse);
        assertEquals(getUserResponse(), userResponse);

        then(userRepository).should(times(1)).findById(USER_ID);
    }

    @Test
    void getById_Should_Throw_Exception_When_UserNotFound() {
        given(userRepository.findById(USER_ID)).willReturn(Optional.empty());

        var ex = assertThrows(UserNotFoundException.class,
                () -> userService.getById(USER_ID));

        assertEquals(USER_NOT_FOUND, ex.getCode());
        assertEquals(String.format("User with id %d was not found", USER_ID), ex.getMessage());

        then(userRepository).should(times(1)).findById(USER_ID);
    }
}