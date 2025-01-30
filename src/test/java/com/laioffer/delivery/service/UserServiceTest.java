package com.laioffer.delivery.service;

import com.laioffer.delivery.dto.UserDto;
import com.laioffer.delivery.model.User;
import com.laioffer.delivery.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetUserById() {
        User mockUser = new User();
        mockUser.setId("123");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("password123");
        mockUser.setName("Test User");

        when(userRepository.findById("123")).thenReturn(java.util.Optional.of(mockUser));

        User result = userService.getUserById("123");

        assertNotNull(result);
        assertEquals("123", result.getId());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test User", result.getName());

        verify(userRepository, times(1)).findById("123");
    }

    @Test
    void shouldCreateUser() {
        UserDto newUserDto = new UserDto();
        newUserDto.setEmail("newuser@example.com");
        newUserDto.setPassword("newpassword123");

        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);

        User savedUser = new User();
        savedUser.setEmail("newuser@example.com");
        savedUser.setName("New User");
        savedUser.setPassword("$2a$10$dummydummydummydummydum");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(newUserDto);

        assertNotNull(result);
        assertEquals("newuser@example.com", result.getEmail());
        assertEquals("New User", result.getName());
        // 对密码是否加密可以做更精细的断言，这里简单判断非空
        assertNotNull(result.getPassword());
        assertNotEquals("newpassword123", result.getPassword()); // 这里不再是明文

        verify(userRepository, times(1)).existsByEmail("newuser@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }
}
