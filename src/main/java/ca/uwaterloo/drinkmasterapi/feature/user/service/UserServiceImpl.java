package ca.uwaterloo.drinkmasterapi.feature.user.service;

import ca.uwaterloo.drinkmasterapi.feature.user.dto.SobrietyTestRequestDTO;
import ca.uwaterloo.drinkmasterapi.handler.exception.InvalidCredentialsException;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.LoginRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.UserResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.SignupRequestDTO;
import ca.uwaterloo.drinkmasterapi.dao.User;
import ca.uwaterloo.drinkmasterapi.handler.exception.ResourceNotFoundException;
import ca.uwaterloo.drinkmasterapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
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
        LocalDateTime currentTime = LocalDateTime.now().withNano(0);

        user.setUsername(signupRequest.getEmail());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setDateOfBirth(signupRequest.getDateOfBirth());
        user.setIsEnabled(true);
        user.setCreatedAt(currentTime);
        user.setSignedInAt(currentTime);
        user.setModifiedAt(currentTime);
        user.setLastSobrietyTestAt(currentTime);

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

    @Override
    public UserResponseDTO updateSobrietyTestScore(SobrietyTestRequestDTO sobrietyTestRequestDTO) {
        User user = userRepository.findById(sobrietyTestRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + sobrietyTestRequestDTO.getUserId() + " not found."));

        LocalDateTime currentTime = LocalDateTime.now().withNano(0);
        user.setLastSobrietyTestScore(sobrietyTestRequestDTO.getScore());
        user.setLastSobrietyTestAt(currentTime);
        user.setModifiedAt(currentTime);
        userRepository.save(user);

        return new UserResponseDTO(user);
    }

    @Override
    public UserResponseDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));

        return new UserResponseDTO(user);
    }
}
