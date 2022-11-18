package com.neko.userservice.service;

import com.neko.userservice.model.entity.User;
import com.neko.userservice.model.request.UserRequest;
import com.neko.userservice.model.request.UserUpdateRequest;
import com.neko.userservice.model.response.UserResponse;

public interface UserService {

    UserResponse register(UserRequest userRequest);

    UserResponse update(UserUpdateRequest userRequest, String id);

    void delete(String id);

    UserResponse findById(String id);

}
