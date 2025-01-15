package com.laioffer.delivery.service;

import com.laioffer.delivery.model.User;
import com.laioffer.delivery.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository; // 模拟 UserRepository

    @InjectMocks
    private UserService userService; // 注入 Mock 对象到 UserService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // 初始化 Mock 对象
    }

    @Test
    void shouldGetUserById() {
        // 模拟返回的用户数据
        User mockUser = new User();
        mockUser.setId("123");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("password123");
        mockUser.setName("Test User");

        // 定义 Repository 的行为
        when(userRepository.findById("123")).thenReturn(java.util.Optional.of(mockUser));

        // 调用 Service 方法
        User result = userService.getUserById("123");

        // 验证结果
        assertNotNull(result);
        assertEquals("123", result.getId());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test User", result.getName());

        // 验证 Repository 被正确调用
        verify(userRepository, times(1)).findById("123");
    }

    @Test
    void shouldCreateUser() {
        // 模拟用户输入
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("newpassword123");
        newUser.setName("New User");

        // 模拟 Repository 的保存行为
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // 调用 Service 方法
        User result = userService.createUser(newUser);

        // 验证结果
        assertNotNull(result);
        assertEquals("newuser@example.com", result.getEmail());
        assertEquals("New User", result.getName());

        // 验证 Repository 被正确调用
        verify(userRepository, times(1)).save(any(User.class));
    }
}
