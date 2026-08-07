/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entregas.frontendentregas.controller;
import com.entregas.frontendentregas.model.UserDTO;
import com.entregas.frontendentregas.model.UserRequestDTO;
import com.entregas.frontendentregas.service.AuthRestClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tools.jackson.databind.ObjectMapper;

/**
 *
 * @author Aluno
 */
@Controller
public class AuthController {
    @Autowired
    public AuthRestClientService authservice;
    
    @GetMapping("/")
    public String home(
    Model model, HttpSession session        
    ){String token = (String) session.getAttribute("token");
        
     if (token == null || token.isBlank()) {
    return "redirect:/login";
}     model.addAttribute("entrega", authservice.listarEntEnd(token));
      model.addAttribute("motorista", authservice.listarMoto(token));
      model.addAttribute("endereco", authservice.listarEndereco(token));
       
     return "index";
    }
    @GetMapping("/login")
    public String login(
            Model model
    ){
        UserRequestDTO credenciais = new UserRequestDTO();
        model.addAttribute("credenciais", credenciais);
        return "login";
    }
    
@PostMapping("/logar")
public String logar(
        @ModelAttribute UserRequestDTO credenciais,
        HttpSession session,
        RedirectAttributes redirectAttributes) {

    try {
        String token = authservice.logar(credenciais);

        session.setAttribute("token", token);

        return "redirect:/";

    } catch (Exception e) {

        redirectAttributes.addFlashAttribute("erroLogin", "Usuário ou senha inválidos.");
        return "redirect:/login";
    }
}
    @GetMapping("/registrar")
    public String registrar(Model model) {

        model.addAttribute("user", new UserDTO());

        return "registrar";
    }
    @PostMapping("/registrar")
    public String mandarRegistro(
            @ModelAttribute UserDTO user,
            RedirectAttributes redirectAttributes) {

        try {

            authservice.registrar(user);

            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Cadastro realizado com sucesso!"
            );

            return "redirect:/login";

        } catch (HttpStatusCodeException ex) {

            String mensagemErroDoBackend = new ObjectMapper()
                    .readTree(ex.getResponseBodyAsString())
                    .get("message")
                    .asText();

            redirectAttributes.addFlashAttribute("erroServidor",mensagemErroDoBackend
            );

            return "redirect:/registrar";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("erroServidor",e.getMessage()
            );

            return "redirect:/registrar";
        }
}
    @GetMapping("/atualizar")
    public String atualizar(HttpSession session, Model model) {
    String token = (String) session.getAttribute("token");

    if (token == null) {
        return "redirect:/login";
    }

    model.addAttribute("user", new UserDTO());
    return "registrar";
}
    @PostMapping("/atualizar")
    public String atualizar(@ModelAttribute UserDTO user,
                            RedirectAttributes redirectAttributes) {
        try {
            authservice.atualizar(user);
            redirectAttributes.addFlashAttribute(
                "mensagem", 
                "Senha atualizada com sucesso!"
            );
            return "redirect:/login";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "erro",
                "Não foi possível atualizar a senha."
            );
            return "redirect:/atualizar";
        }
    }    
}