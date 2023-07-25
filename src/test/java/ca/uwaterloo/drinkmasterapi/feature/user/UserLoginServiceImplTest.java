package ca.uwaterloo.drinkmasterapi.feature.user;

import ca.uwaterloo.drinkmasterapi.common.InvalidCredentialsException;
import ca.uwaterloo.drinkmasterapi.feature.user.model.LoginRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.SignupRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.User;
import ca.uwaterloo.drinkmasterapi.feature.user.model.UserResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.repository.UserRepository;
import ca.uwaterloo.drinkmasterapi.feature.user.service.UserLoginServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class UserLoginServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserLoginServiceImpl userLoginService;

    @Test
    public void testSignup_WithValidCredentials_Success() {
        // Arrange
        SignupRequestDTO signupRequest = new SignupRequestDTO();
        signupRequest.setEmail("valid@example.com");
        signupRequest.setPassword("password123");

        User createdUser = new User();
        createdUser.setId(1L);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(createdUser);

        // Act
        UserResponseDTO responseDTO = userLoginService.signup(signupRequest);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
    }

    @Test
    void testSignup_WithAlreadyRegisteredEmail_ThrowsInvalidCredentialsException() {
        // Arrange
        SignupRequestDTO signupRequest = new SignupRequestDTO();
        signupRequest.setEmail("existing@example.com");
        signupRequest.setPassword("password123");

        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);

        // Act and Assert
        assertThrows(InvalidCredentialsException.class, () -> userLoginService.signup(signupRequest));
    }

    @Test
    public void testLogin_WithValidCredentials_Success() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        User user = new User();
        user.setId(1L);

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserResponseDTO responseDTO = userLoginService.login(loginRequest);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
    }

    @Test
    public void testLogin_WithInvalidEmail_ThrowInvalidCredentialsException() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("nonexistent@example.com");
        loginRequest.setPassword("password123");

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(InvalidCredentialsException.class, () -> userLoginService.login(loginRequest));
    }

    @Test
    void testLogin_InvalidPassword_ThrowsInvalidCredentialsException() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("incorrect_password");

        User user = new User();
        user.setPassword("encoded_password");

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

        // Act and Assert
        assertThrows(InvalidCredentialsException.class, () -> userLoginService.login(loginRequest));
    }
}
