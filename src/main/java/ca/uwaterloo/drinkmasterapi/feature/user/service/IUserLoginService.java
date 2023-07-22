package ca.uwaterloo.drinkmasterapi.feature.user.service;

import ca.uwaterloo.drinkmasterapi.feature.user.model.LoginRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.UserResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.SignupRequestDTO;

public interface IUserLoginService {
    UserResponseDTO signup(SignupRequestDTO signupRequest);

    UserResponseDTO login(LoginRequestDTO loginRequest);
}
