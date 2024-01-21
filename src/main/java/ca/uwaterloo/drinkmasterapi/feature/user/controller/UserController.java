package ca.uwaterloo.drinkmasterapi.feature.user.controller;

import ca.uwaterloo.drinkmasterapi.common.ErrorResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.LoginRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.UserResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.SignupRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.service.IUserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    private final IUserService loginService;

    @Autowired
    public UserController(IUserService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Login successful", response = UserResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponseDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        UserResponseDTO user = loginService.login(loginRequest);
        // Perform additional actions or return response as needed
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Signup successful", response = UserResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponseDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDTO signupRequest) {
        UserResponseDTO user = loginService.signup(signupRequest);
        // Perform additional actions or return response as needed
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
