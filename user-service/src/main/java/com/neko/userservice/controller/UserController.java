package com.neko.userservice.controller;


import com.neko.userservice.exception.DataAlreadyExistsException;
import com.neko.userservice.exception.DataNotFoundException;
import com.neko.userservice.exception.GlobalExceptionHandler;
import com.neko.userservice.exception.NotMatchException;
import com.neko.userservice.model.request.UserRequest;
import com.neko.userservice.model.request.UserUpdateRequest;
import com.neko.userservice.model.response.GlobalResponseHandler;
import com.neko.userservice.model.response.UserResponse;
import com.neko.userservice.model.response.WebResponse;
import com.neko.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/api/user/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/sign-up")
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> postRequestRegister(@Valid @RequestBody UserRequest userRequest) {
        log.info("#calling controller postRequestRegister");
        try {
            UserResponse userResponse = userService.register(userRequest);
            log.info("#successfully register user with email {} ", userResponse.getEmail());
            return GlobalResponseHandler
                    .generateResponse("successfully register user with id "  + userResponse.getId() , HttpStatus.OK, userResponse);
        }catch (DataAlreadyExistsException exception) {
            log.error("error {} ", exception.getMessage());
            log.info("#failed register user with email {} ", userRequest.getEmail());
            return GlobalExceptionHandler.dataAlreadyExistsHandler(exception.getMessage());
        }
    }


    @PutMapping(value = "/update/{id}")
    @ResponseBody
    public ResponseEntity<?> postRequestUpdate(@Valid @RequestBody UserUpdateRequest userUpdateRequest , @PathVariable String id) {
        log.info("#calling controller postRequestUpdate");
        try {
            UserResponse userResponse = userService.update(userUpdateRequest, id);
            log.info("#successfully update user with id {} ", id);
            return GlobalResponseHandler
                    .generateResponse("successfully update user with id "  + userResponse.getId() , HttpStatus.OK, userResponse);
        }catch (DataNotFoundException | NotMatchException exception) {
            log.info("#failed update user with id {}, error {} ", id, exception.getMessage());
            return GlobalExceptionHandler.dataNotFoundHandler(exception.getMessage());
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> requestDeleteUserAccount(@PathVariable("id") String id) {
        log.info("#calling controller requestDeleteUserAccount");
        try {
            userService.delete(id);
            log.info("#successfully delete user with id {}", id);
            return GlobalResponseHandler
                    .generateResponse("successfully delete data with id "  + id ,HttpStatus.OK, null);
        }catch (DataNotFoundException exception) {
            log.info("#failed delete user with id {}", id);
            return GlobalExceptionHandler.dataNotFoundHandler(exception.getMessage());
        }
    }

    @GetMapping(value = "/get/{id}")
    @ResponseBody
    public ResponseEntity<WebResponse<UserResponse>> getUserById(@PathVariable("id") String id) {
        log.info("#calling controller get user by id");
        try {
            UserResponse userResponse = userService.findById(id);
            log.info("#successfully get data user with id {}", id);
            return new ResponseEntity<>(
                   new WebResponse<>(
                           HttpStatus.OK.value(),
                           HttpStatus.OK.getReasonPhrase(),
                           userResponse
                   ),
                    HttpStatus.OK
            );
        }catch (DataNotFoundException exception) {
            log.info("#failed delete user with id {}", id);
            return new ResponseEntity<>(
                    new WebResponse<>(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase(),
                            null
                    ),
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
