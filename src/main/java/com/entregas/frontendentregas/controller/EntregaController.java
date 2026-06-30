/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entregas.frontendentregas.controller;

import com.entregas.frontendentregas.model.EntregaDTO;
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
@RequestMapping("/entrega")
public class EntregaController {
    @Autowired
    private AuthRestClientService service;
    @GetMapping("/list")
    public String listar(HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");

        if (token == null || token.isBlank()) {
            return "redirect:/login";
        }

        model.addAttribute("entrega", service.listarEntrega(token));

        return "entrega";
    }
    @PostMapping("/criarE")
    public String criar(@ModelAttribute EntregaDTO entrega, HttpSession session) {

        String token = (String) session.getAttribute("token");

        service.criarEntrega(entrega, token);

        return "criarE";
    }
    @GetMapping("/editarE")
    public String editar(@RequestParam Long id, Model model) {

    model.addAttribute("entrega", service.buscarEntrega(id));

    return "editarE";
}
    @PostMapping("/salvar")
    public String salvarDados(@ModelAttribute  EntregaDTO entrega){
    service.atualizarEntrega(entrega);
    return "redirect:/entrega/list";        
    }    
}    
