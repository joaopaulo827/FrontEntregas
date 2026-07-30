/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entregas.frontendentregas.controller;


import com.entregas.frontendentregas.model.EnderecoDTO;
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
 * @author Aluno
 */
@Controller
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private AuthRestClientService service;
    @GetMapping("/list")
    public String listar(HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");

        if (token == null || token.isBlank()) {
            return "redirect:/login";
        }

        model.addAttribute("endereco", service.listarEndereco(token));

        return "endereco";
    }
    @GetMapping("/adicionarED")
    public String criarForm(Model model) {
        model.addAttribute("endereco", new EnderecoDTO());
        return "adicionarED";
    }
    
    @PostMapping("/adicionarED")
    public String criar(@ModelAttribute EnderecoDTO endereco, HttpSession session) {

        String token = (String) session.getAttribute("token");

        service.adicionarEndereco(endereco, token);

        return "redirect:/endereco/list";
    }    
    @GetMapping("/editarED")
    public String editar(@RequestParam Long id, Model model) {

    model.addAttribute("endereco", service.buscarEndereco(id));

    return "editarED";
}
@PostMapping("/salvar")
public String salvarDados(@ModelAttribute EnderecoDTO endereco){

    EnderecoDTO motoristaAtual = service.buscarEndereco(endereco.getId());

    endereco.setId(motoristaAtual.getId());

    service.atualizarEndereco(endereco);

    return "redirect:/endereco/list";        
}     
}
