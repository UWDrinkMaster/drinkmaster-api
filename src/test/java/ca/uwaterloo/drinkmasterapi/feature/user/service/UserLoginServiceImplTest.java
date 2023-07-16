package ca.uwaterloo.drinkmasterapi.feature.user.service;

import ca.uwaterloo.drinkmasterapi.feature.user.model.LoginRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.User;
import ca.uwaterloo.drinkmasterapi.feature.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserLoginServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserLoginServiceImpl loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLogin_ValidCredentials_ReturnsUser() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        // Act
        User result = loginService.login(loginRequest);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());

        // Verify that the userRepository.findByEmail method was called with the correct email parameter
        verify(userRepository).findByEmail(email);
    }

    @Test
    void testLogin_InvalidEmail_ThrowsException() {
        // Arrange
        String email = "test@example.com";
        String password = "password";

        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> loginService.login(loginRequest));
    }

    @Test
    void testLogin_InvalidPassword_ThrowsException() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> loginService.login(loginRequest));
    }
}
