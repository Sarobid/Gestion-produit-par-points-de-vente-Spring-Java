package com.example.ultraspringmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

@Controller
public class EmailController {
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/nouveauMdp")
    public String nouveauMdp(){
        return "email";
    }

    @GetMapping("/send-email")
    public String sendEmail( @RequestParam(defaultValue = "") String mail, HttpServletRequest request)throws Exception{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(mail);
        helper.setSubject("nouveau mot de passe");
        String htmlContent = "<h1>Nouveau mot de passe</h1> <a href='localhost:8080/"+request.getContextPath()+"/'>confirmer</a>";
        helper.setText(htmlContent, true); // true indique que le contenu est du HTML
        mailSender.send(message);
        return "email";
    }
}
