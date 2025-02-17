package org.sparta.jwt.sample.service;

import org.sparta.jwt.sample.config.JwtUtils;
import org.sparta.jwt.sample.constants.UserRole;
import org.sparta.jwt.sample.controller.dto.ResDto;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final JwtUtils jwtUtils;

    public LoginService(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public ResDto login() {
        String bearerToken = jwtUtils.createToken(1L, UserRole.ROLE_USER);
        return new ResDto(bearerToken);
    }
}
