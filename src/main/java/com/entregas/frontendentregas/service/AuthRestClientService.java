/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entregas.frontendentregas.service;

import com.entregas.frontendentregas.model.EnderecoDTO;
import com.entregas.frontendentregas.model.EntregaDTO;
import com.entregas.frontendentregas.model.MotoDTO;
import com.entregas.frontendentregas.model.UserDTO;
import com.entregas.frontendentregas.model.UserRequestDTO;
import java.util.Arrays;
import java.util.List;
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
        if (user.getSenha() == null || !user.getSenha().equals(user.getConfirmarSenha())) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(400),
                    "Senha e Confirmar Senha Diferentes");
        }
        
         user.setRole("MOTORISTA");
         
        restClient.post()
                .uri("/auth/registrar")
                .body(user)
                .retrieve()
                .body(String.class);
    }   
    public void atualizar(UserDTO user) {
        restClient.put()
                .uri("/auth/atualizar")
                .body(user)
                .retrieve()
                .body(String.class);
    }    
    public List<MotoDTO> listarMoto(String token) {
        MotoDTO[] motorista = restClient.get()
                .uri("/auth/motorista")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(MotoDTO[].class);

        return Arrays.asList(motorista);
    }
    public List<EntregaDTO> listarEntrega(String token) {
        EntregaDTO[] entregas = restClient.get()
                .uri("/auth/entrega")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(EntregaDTO[].class);

        return Arrays.asList(entregas);
    }
    public List<EnderecoDTO> listarEndereco(String token) {
        EnderecoDTO[] endereco = restClient.get()
                .uri("/auth/endereco")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(EnderecoDTO[].class);

        return Arrays.asList(endereco);
    }    
    public EntregaDTO buscarEntrega(Long id) {
    return restClient.get()
            .uri("/auth/entrega/{id}", id)
            .retrieve()
            .body(EntregaDTO.class);
}
    public void atualizarEntrega(EntregaDTO entrega) {
    restClient.put()
            .uri("/auth/entrega/{id}", entrega.getId())
            .body(entrega)
            .retrieve()
            .body(String.class);
}
    public void atualizarStatus(EntregaDTO entrega) {
    restClient.put()
            .uri("/auth/entrega/{id}/status", entrega.getId())
            .body(entrega)
            .retrieve()
            .body(String.class);
}    
    public void atualizarIdMotorista(EntregaDTO entrega) {
    restClient.put()
            .uri("/auth/entrega/{id}/motorista", entrega.getId())
            .body(entrega)
            .retrieve()
            .body(String.class);
}    
    public void criarEntrega(EntregaDTO entrega, String token) {
    restClient.post()
            .uri("/auth/entrega/criar")
            .header("Authorization", "Bearer " + token)
            .body(entrega)
            .retrieve()
            .toBodilessEntity();
}
    public MotoDTO buscarMotorista(Long id) {
    return restClient.get()
            .uri("/auth/motorista/{id}", id)
            .retrieve()
            .body(MotoDTO.class);
}
    public void atualizarMotorista(MotoDTO motorista) {
    restClient.put()
            .uri("/auth/motorista/{id}", motorista.getId())
            .body(motorista)
            .retrieve()
            .body(String.class);
}
    public void adicionarMoto(MotoDTO moto, String token) {
    restClient.post()
            .uri("/auth/motorista/adicionarM")
            .header("Authorization", "Bearer " + token)
            .body(moto)
            .retrieve()
            .toBodilessEntity();
}
    public EnderecoDTO buscarEndereco(Long id) {
    return restClient.get()
            .uri("/auth/endereco/{id}", id)
            .retrieve()
            .body(EnderecoDTO.class);
}
    public void atualizarEndereco(EnderecoDTO endereco) {
    restClient.put()
            .uri("/auth/endereco/{id}", endereco.getId())
            .body(endereco)
            .retrieve()
            .body(String.class);
}
    public void adicionarEndereco(EnderecoDTO endereco, String token) {
    restClient.post()
            .uri("/auth/endereco/adicionarED")
            .header("Authorization", "Bearer " + token)
            .body(endereco)
            .retrieve()
            .toBodilessEntity();
}     
}
