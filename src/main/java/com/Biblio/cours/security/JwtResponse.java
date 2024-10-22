package com.Biblio.cours.security;


import lombok.Getter;
import lombok.Setter;

@Getter

public class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}