package ca.uwaterloo.drinkmasterapi.feature.user.service;

import ca.uwaterloo.drinkmasterapi.common.InvalidCredentialsException;
import ca.uwaterloo.drinkmasterapi.feature.user.model.LoginRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.UserResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.SignupRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.User;
import ca.uwaterloo.drinkmasterapi.feature.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserLoginServiceImpl implements IUserLoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLoginServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO signup(SignupRequestDTO signupRequest) {
        // Check if the email is already registered
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new InvalidCredentialsException("Email is already registered: " + signupRequest.getEmail());
        }

        // Create a new user entity
        User user = new User();

        user.setUsername(signupRequest.getEmail());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setIsEnabled(true);

        // Save the user to the database
        User createdUser = userRepository.save(user);

        return new UserResponseDTO(createdUser);
    }

    @Override
    public UserResponseDTO login(LoginRequestDTO loginRequest) {
        // Find the user by email or throw InvalidCredentialsException if not found
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email: " + loginRequest.getEmail()));

        // Check if the provided password matches the stored password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password: " + loginRequest.getPassword());
        }

        // Set the signedInAt field to the current timestamp
        user.setSignedInAt(LocalDateTime.now().withNano(0));
        User updatedUser = userRepository.save(user);

        return new UserResponseDTO(updatedUser);
    }
}
