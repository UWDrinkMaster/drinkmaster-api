package ca.uwaterloo.drinkmasterapi.feature.user.service;

import ca.uwaterloo.drinkmasterapi.feature.user.dto.LoginRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.UserResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.SignupRequestDTO;

public interface IUserService {
    UserResponseDTO signup(SignupRequestDTO signupRequest);

    UserResponseDTO login(LoginRequestDTO loginRequest);
}
