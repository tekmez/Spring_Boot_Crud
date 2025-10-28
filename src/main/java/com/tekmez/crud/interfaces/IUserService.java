package com.tekmez.crud.interfaces;

import com.tekmez.crud.model.dto.RegisterRequest;
import com.tekmez.crud.model.dto.UserResponse;

import java.util.List;

public interface IUserService {

    UserResponse register(RegisterRequest request);
    List<UserResponse> getUsers();
}
