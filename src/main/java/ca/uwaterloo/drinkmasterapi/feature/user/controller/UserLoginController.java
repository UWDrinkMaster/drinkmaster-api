package ca.uwaterloo.drinkmasterapi.feature.user.controller;

import ca.uwaterloo.drinkmasterapi.feature.user.model.LoginRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.SignupRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.User;
import ca.uwaterloo.drinkmasterapi.feature.user.service.IUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserLoginController {
    private final IUserLoginService loginService;

    @Autowired
    public UserLoginController(IUserLoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        User user = loginService.login(loginRequest);
        // Perform additional actions or return response as needed
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDTO signupRequest) {
        loginService.signup(signupRequest);
        // Perform additional actions or return response as needed
        return ResponseEntity.ok().build();
    }
}
