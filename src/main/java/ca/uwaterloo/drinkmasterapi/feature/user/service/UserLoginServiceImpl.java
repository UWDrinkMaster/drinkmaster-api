package ca.uwaterloo.drinkmasterapi.feature.user.service;

import ca.uwaterloo.drinkmasterapi.common.InvalidCredentialsException;
import ca.uwaterloo.drinkmasterapi.feature.user.model.LoginRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.LoginResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.SignupRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.User;
import ca.uwaterloo.drinkmasterapi.feature.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
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
    public void signup(SignupRequestDTO signupRequest) {
        // Check if the email is already registered
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            log.info("Email is already registered: {}", signupRequest.getEmail());
            throw new InvalidCredentialsException("Email is already registered");
        }

        // Create a new user entity
        User user = new User();
        LocalDateTime currentTime = LocalDateTime.now().withNano(0);

        user.setUsername(signupRequest.getEmail());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setIsEnabled(true);
        user.setLastSobrietyTestAt(currentTime);
        user.setModifiedAt(currentTime);
        user.setSignedInAt(currentTime);
        user.setModifiedAt(currentTime);

        // Save the user to the database
        userRepository.save(user);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        // Find the user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    log.error("Invalid email : {}", loginRequest.getEmail());
                    return new InvalidCredentialsException("Invalid email or password");
                });

        // Check if the provided password matches the stored password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            log.error("Invalid password: {}", loginRequest.getPassword());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // Set the signedInAt field to the current timestamp
        user.setSignedInAt(LocalDateTime.now().withNano(0));
        User updatedUser = userRepository.save(user);

        return new LoginResponseDTO(updatedUser);
    }
}
