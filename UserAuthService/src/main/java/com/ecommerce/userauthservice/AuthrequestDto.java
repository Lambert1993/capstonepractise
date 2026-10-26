package com.ecommerce.userauthservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthrequestDto {

    private String email;

    private String password;

    private Role role;
}
