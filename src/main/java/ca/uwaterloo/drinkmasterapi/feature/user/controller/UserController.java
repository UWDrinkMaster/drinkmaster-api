package ca.uwaterloo.drinkmasterapi.feature.user.controller;

import ca.uwaterloo.drinkmasterapi.common.ErrorResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.LoginRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.SobrietyTestRequestDTO;
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
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Login successful", response = UserResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponseDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        UserResponseDTO user = userService.login(loginRequest);
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
        UserResponseDTO user = userService.signup(signupRequest);
        // Perform additional actions or return response as needed
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping(value = "/sobriety_test", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sobriety score updated successfully", response = UserResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponseDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    public ResponseEntity<?> updateSobrietyTestScore(@Valid @RequestBody SobrietyTestRequestDTO sobrietyTestRequestDTO) {
        UserResponseDTO user = userService.updateSobrietyTestScore(sobrietyTestRequestDTO);
        // Perform additional actions or return response as needed
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = UserResponseDTO.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponseDTO.class)
    })
    public ResponseEntity<?> getUserProfileByUserId(@PathVariable Long userId) {
        UserResponseDTO responseDTOs = userService.getUserProfile(userId);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }
}
