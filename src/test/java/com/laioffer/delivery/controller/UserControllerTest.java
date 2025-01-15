package com.laioffer.delivery.controller;

import com.laioffer.delivery.model.User;
import com.laioffer.delivery.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService; // 使用 @Mock 模拟 Service

    @InjectMocks
    private UserController userController; // 使用 @InjectMocks 注入 Mock 对象

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // 初始化 Mock 对象
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); // 使用 MockMvc 构建测试环境
    }

    @Test
    void shouldReturnNotFoundIfUserNotExists() throws Exception {
        // 模拟 UserService 返回 null
        when(userService.getUserById("99")).thenReturn(null);

        // 模拟 HTTP 请求并验证结果
        mockMvc.perform(get("/api/users/99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}