package com.ecommerce.userauthservice.controller;

import com.ecommerce.userauthservice.dto.AuthRequest;
import com.ecommerce.userauthservice.dto.LoginResponse;
import com.ecommerce.userauthservice.dto.UserDto;
import com.ecommerce.userauthservice.model.User;
import com.ecommerce.userauthservice.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody AuthRequest authRequest) {
        User user = authService.signUp(authRequest.getEmail(),
                authRequest.getPassword());
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setId(user.getId());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        return ResponseEntity.ok(response);
    }

}