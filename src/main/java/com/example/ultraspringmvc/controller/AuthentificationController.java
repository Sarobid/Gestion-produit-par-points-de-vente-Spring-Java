package com.example.ultraspringmvc.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthentificationController {

    @GetMapping("/")
    public String index(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Récupérer l'authentication actuelle de l'utilisateur
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            // Vider l'authentification actuelle de l'utilisateur
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        // Rediriger vers la page de login après la déconnexion réussie
        return "redirect:/";
    }
}
