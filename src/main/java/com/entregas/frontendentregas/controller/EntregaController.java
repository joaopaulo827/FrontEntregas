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
import org.springframework.web.bind.annotation.ResponseBody;

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
    @GetMapping("/endereco")
    public String listarEndereco(HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");

        if (token == null || token.isBlank()) {
            return "redirect:/login";
        }

        model.addAttribute("entrega", service.listarEntEnd(token));

        return "entregaEndereco";
    }    
    @GetMapping("/criar")
    public String criarForm(Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");

        if (token == null || token.isBlank()) {
            return "redirect:/login";
        }
        model.addAttribute("entrega", new EntregaDTO());
        model.addAttribute("enderecos", service.listarEndereco(token));

        return "criar";
    }
    
    @PostMapping("/criar")
    public String criar(@ModelAttribute EntregaDTO entrega, HttpSession session) {

        String token = (String) session.getAttribute("token");

        service.criarEntrega(entrega, token);

        return "redirect:/entrega/list";
    }
    @GetMapping("/editarE")
    public String editar(@RequestParam Long id, Model model, HttpSession session) {
        
    String token = (String) session.getAttribute("token");

    if (token == null || token.isBlank()) {
        return "redirect:/login";
    }
    model.addAttribute("entrega", service.buscarEntrega(id));
     model.addAttribute("enderecos", service.listarEndereco(token));
    return "editarE";
}
    @GetMapping("/motoristaE")
    public String editarMotorista(@RequestParam Long id,HttpSession session,Model model) {

        String token = (String) session.getAttribute("token");

        if (token == null || token.isBlank()) {
            return "redirect:/login";}
        
        model.addAttribute("entrega", service.buscarEntrega(id));
        model.addAttribute("motoristas", service.listarMotoristasAtivos(token));
        return "motoristaE";
    }
    @GetMapping("/statusE")
    public String editarStatus(@RequestParam Long id, Model model) {

    model.addAttribute("entrega", service.buscarEntrega(id));

    return "statusE";
}    
    @PostMapping("/salvar")
    public String salvarDados(@ModelAttribute  EntregaDTO entrega){
    service.atualizarEntrega(entrega);
    return "redirect:/entrega/list";        
    }
    @PostMapping("/salvarMotorista")
    public String salvarMotorista(EntregaDTO entrega) {
        service.atualizarIdMotorista(entrega);
        return "redirect:/entrega/list";
    }
    @PostMapping("/salvarStatus")
    public String salvarStatus(EntregaDTO entrega) {
        service.atualizarStatus(entrega);
        return "redirect:/entrega/list";
    }    
}    
