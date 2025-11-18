package com.adil.usermanagementservice.controller;

import com.adil.usermanagementservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.adil.usermanagementservice.common.JsonFiles.USER_RESPONSE;
import static com.adil.usermanagementservice.common.TestUtils.json;
import static com.adil.usermanagementservice.common.UserTestConstant.USER_ID;
import static com.adil.usermanagementservice.common.UserTestConstant.getUserCreateRequest;
import static com.adil.usermanagementservice.common.UserTestConstant.getUserResponse;
import static com.adil.usermanagementservice.common.UserTestConstant.getUserUpdateRequest;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final String BASE_URL = "/api/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void create_Should_Return_Success() throws Exception {
        given(userService.create(getUserCreateRequest())).willReturn(getUserResponse());

        mockMvc.perform(post(BASE_URL)
                        .content(objectMapper.writeValueAsString(getUserCreateRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(json(USER_RESPONSE)));

        then(userService).should(times(1)).create(getUserCreateRequest());
    }

    @Test
    void update_Should_Return_Success() throws Exception {
        given(userService.update(USER_ID, getUserUpdateRequest())).willReturn(getUserResponse());

        mockMvc.perform(put(BASE_URL + "/{id}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUserUpdateRequest())))
                .andExpect(status().isOk())
                .andExpect(content().json(json(USER_RESPONSE)));

        then(userService).should(times(1))
                .update(USER_ID, getUserUpdateRequest());
    }
}