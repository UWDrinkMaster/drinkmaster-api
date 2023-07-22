package ca.uwaterloo.drinkmasterapi.feature.user.service;

import ca.uwaterloo.drinkmasterapi.common.InvalidCredentialsException;
import ca.uwaterloo.drinkmasterapi.feature.user.model.LoginRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.SignupRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.User;
import ca.uwaterloo.drinkmasterapi.feature.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
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
    void testSignup_EmailAlreadyRegistered_ThrowsInvalidCredentialsException() {
        // Arrange
        String email = "test@example.com";
        String password = "password";

        SignupRequestDTO signupRequest = new SignupRequestDTO();
        signupRequest.setEmail(email);
        signupRequest.setPassword(password);

        // Assume user with email already exists in the database
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // Act and Assert
        assertThrows(InvalidCredentialsException.class, () -> loginService.signup(signupRequest));
    }

    @Test
    void testLogin_InvalidEmail_ThrowsInvalidCredentialsException() {
        // Arrange
        String email = "test@example.com";
        String password = "password";

        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(InvalidCredentialsException.class, () -> loginService.login(loginRequest));
    }

    @Test
    void testLogin_InvalidPassword_ThrowsInvalidCredentialsException() {
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
        assertThrows(InvalidCredentialsException.class, () -> loginService.login(loginRequest));
    }
}
