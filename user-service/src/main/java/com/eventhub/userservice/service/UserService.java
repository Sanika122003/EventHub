package com.eventhub.userservice.service;

import com.eventhub.userservice.dto.request.LoginRequest;
import com.eventhub.userservice.dto.request.RegisterRequest;
import com.eventhub.userservice.dto.response.LoginResponse;
import com.eventhub.userservice.dto.response.UserProfileResponse;

public interface UserService {

    void registerUser(RegisterRequest request);

    LoginResponse loginUser(LoginRequest request);

    UserProfileResponse getProfile();

}