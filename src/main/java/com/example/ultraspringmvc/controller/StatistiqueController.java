package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.exception.BadRequestException;
import com.example.ultraspringmvc.model.Statistique;
import com.example.ultraspringmvc.proprety.SpringConvert;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/admin/state")
public class StatistiqueController {
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
                value = "redirect:/admin/state/" + e.getFunction();
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

    @GetMapping("/statistique")
    public String state(Model model,@RequestParam(defaultValue = "") String mois,@RequestParam(defaultValue = "0") int annee)throws Exception{
        Statistique statistique = new Statistique();
        model.addAttribute("vente-globale",statistique.venteParMoisGlobal(mois,annee));
        model.addAttribute("vente-points-vente",statistique.venteParMoisParPointsVente(mois,annee));
        model.addAttribute("mois",mois);
        model.addAttribute("annee",String.valueOf(annee));
        return "statistique";
    }


    @GetMapping("/benefice")
    public String benefice(Model model,@RequestParam(defaultValue = "0") int annee)throws Exception{
        Statistique statistique = new Statistique();
        model.addAttribute("benefice",statistique.beneficeParMois(annee));
        model.addAttribute("annee",String.valueOf(annee));
        return "benefice";
    }
    @GetMapping("/pdf/benefice")
    public String beneficpfe(Model model,@RequestParam(defaultValue = "0") int annee)throws Exception{
        Statistique statistique = new Statistique();
        model.addAttribute("benefice",statistique.beneficeParMois(annee));
        return "benefice";
    }




}
