package org.sparta.jwt.sample.controller.dto;

public class ResDto {

    private final String bearerToken;

    public ResDto(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String getBearerToken() {
        return this.bearerToken;
    }
}
