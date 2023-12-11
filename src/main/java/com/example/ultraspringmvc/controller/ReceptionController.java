package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.CustomUser;
import com.example.ultraspringmvc.exception.BadRequestException;
import com.example.ultraspringmvc.model.Etat;
import com.example.ultraspringmvc.model.ReceptionTransfere;
import com.example.ultraspringmvc.model.Transfere;
import com.example.ultraspringmvc.proprety.SpringConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@Service
@RequestMapping("/user/reception")
public class ReceptionController {
    @ExceptionHandler(Exception.class)
    public String handleItemException(RedirectAttributes redirectAttributes, Model model, Exception ex) {
        String value = "error";
        List<FieldError> errors = null;
        ex.printStackTrace();
        BadRequestException e = null;
        if (ex instanceof BadRequestException) {
            e = (BadRequestException) ex;
            if (e.getFunction() != null) {
                errors = e.getBindingResult().getFieldErrors();
                for (FieldError error : errors) {
                    redirectAttributes.addFlashAttribute("error-" + error.getField(), error.getField() + " " + error.getDefaultMessage().substring(error.getDefaultMessage().indexOf(":")));
                }
                value = "redirect:/user/reception/" + e.getFunction();
            } else {
                model.addAttribute("error", e.getBindingResult().getFieldError().getDefaultMessage().substring(e.getBindingResult().getFieldError().getDefaultMessage().indexOf(":") + 1));
            }
        } else {
            model.addAttribute("error", ex.getLocalizedMessage());
        }
        return value;
    }

    @Autowired
    private DataSource dataSource;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalTime.class, new SpringConvert(LocalTime.class));
        binder.registerCustomEditor(LocalDate.class, new SpringConvert(LocalDate.class));
        binder.registerCustomEditor(LocalDateTime.class, new SpringConvert(LocalDateTime.class));
    }

    @GetMapping("/")
    public String reception(Model model) throws Exception {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (customUser.getUtilisateur().getId_points_vente() == null) {
            throw new Exception("vous n'etes pas affecte a aucun points de vente");
        }
        model.addAttribute("etat",new Etat().find(null,null));
        model.addAttribute("points-vente", customUser.getUtilisateur().getId_points_vente());
        model.addAttribute("transfere", new Transfere().findQuery(dataSource.getConnection(), "select * from v_trans_non_recu where id_points_vente=" + customUser.getUtilisateur().getId_points_vente().getId()));
        return "reception";
    }
    @PostMapping("/add")
    public String add(@ModelAttribute ReceptionTransfere reception, RedirectAttributes redirectAttributes)throws Exception{
        reception.nouveauReception();
        redirectAttributes.addFlashAttribute("reussi","veuiller regarder votre stock");
        return "redirect:/user/reception/";
    }
}
