package org.sparta.jwt.sample.controller;

import org.sparta.jwt.sample.controller.dto.ResDto;
import org.sparta.jwt.sample.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping
    public ResDto login() {
        return service.login();
    }
}
