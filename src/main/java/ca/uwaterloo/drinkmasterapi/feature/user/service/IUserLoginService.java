package ca.uwaterloo.drinkmasterapi.feature.user.service;

import ca.uwaterloo.drinkmasterapi.feature.user.model.LoginRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.LoginResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.model.SignupRequestDTO;

public interface IUserLoginService {
    void signup(SignupRequestDTO signupRequest);

    LoginResponseDTO login(LoginRequestDTO loginRequest);
}
