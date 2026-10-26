package com.ecommerce.userauthservice.controller;

import com.ecommerce.userauthservice.dto.UserDto;
import com.ecommerce.userauthservice.model.User;
import com.ecommerce.userauthservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestingAuthorization {

    private final UserRepository userRepo;

    @GetMapping("/findme")
    public ResponseEntity<UserDto> testAuthorization(Authentication authentication){

        System.out.println(authentication.getName());
        Optional<User> optionalUser = userRepo.findByEmail(authentication.getName());
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserDto dto = new UserDto();
        dto.setId(optionalUser.get().getId());
        dto.setEmail(optionalUser.get().getEmail());
        dto.setRole(optionalUser.get().getRole());
        return ResponseEntity.ok(dto);

    }
}
