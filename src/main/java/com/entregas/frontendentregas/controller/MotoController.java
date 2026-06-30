/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entregas.frontendentregas.controller;

import com.entregas.frontendentregas.model.MotoDTO;
import com.entregas.frontendentregas.service.AuthRestClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author joaop
 */
@Controller
@RequestMapping("/motorista")
public class MotoController {

    @Autowired
    private AuthRestClientService service;
    @GetMapping("/list")
    public String listar(HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");

        if (token == null || token.isBlank()) {
            return "redirect:/login";
        }

        model.addAttribute("motorista", service.listarMoto(token));

        return "motorista";
    }
    @GetMapping("/editarM")
    public String editar(@RequestParam Long id, Model model) {

    model.addAttribute("motorista", service.buscarMotorista(id));

    return "editarM";
}
@PostMapping("/salvar")
public String salvarDados(@ModelAttribute MotoDTO motorista){

    MotoDTO motoristaAtual = service.buscarMotorista(motorista.getId());

    motorista.setUsuario_id(motoristaAtual.getUsuario_id());

    service.atualizarMotorista(motorista);

    return "redirect:/motorista/list";        
}    
}
