package com.ecommerce.userauthservice.dto;

import com.ecommerce.userauthservice.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private Role role;
}
