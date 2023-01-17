package com.todolist.ensolvers.security;


public class JwtAuthResponseDto {

    private String token;
    private String tipoDeToken = "Bearer";

    public JwtAuthResponseDto(String token, String tipoDeToken) {

        super();
        this.token = token;
        this.tipoDeToken = tipoDeToken;

    }

    public JwtAuthResponseDto(String token) {

        super();
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoDeToken() {
        return tipoDeToken;
    }

    public void setTipoDeToken(String tipoDeToken) {
        this.tipoDeToken = tipoDeToken;
    }

}
