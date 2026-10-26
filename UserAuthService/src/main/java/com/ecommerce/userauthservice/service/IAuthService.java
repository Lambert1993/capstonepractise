package com.ecommerce.userauthservice.service;

import com.ecommerce.userauthservice.model.User;

public interface IAuthService {
    User signUp(String email, String password);

    String login(String email, String password);
}
