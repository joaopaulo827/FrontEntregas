/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entregas.frontendentregas.service;

import com.entregas.frontendentregas.model.UserDTO;
import com.entregas.frontendentregas.model.UserRequestDTO;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author joaop
 */
@Service
public class AuthRestClientService {
    private final RestClient restClient;

    public AuthRestClientService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:9000/api")
                .build();
    }

    public String logar(UserRequestDTO user) {
        return restClient.post()
                .uri("/auth/logar")
                .body(user)
                .retrieve()
                .body(String.class);
    }

    public void registrar(UserDTO user) {

        if (!user.getSenha().equals(user.getConfirmarSenha())) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(400),
                    "Senha e Confirmar Senha Diferentes");
        }

        user.setRole("CLIENTE");

        restClient.post()
                .uri("/auth/registrar")
                .body(user)
                .retrieve()
                .body(String.class);
    }    
}
