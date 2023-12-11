package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.exception.BadRequestException;
import com.example.ultraspringmvc.model.Benefice;
import com.example.ultraspringmvc.model.Commission;
import com.example.ultraspringmvc.model.Vente;
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
@RequestMapping("/admin/benefice")
public class BeneficeController {
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
                value = "redirect:/admin/benefice/" + e.getFunction();
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

    @GetMapping("/benefice")
    public String benefice(@ModelAttribute Benefice benefice, Model model, @RequestParam(defaultValue = "") String mois, @RequestParam(defaultValue = "0")int annee)throws Exception{
        Benefice[] benefices = benefice.listeBenefice(mois,annee);
        benefice.listeBeneficeAvecCommission(benefices,Commission.listeAll());
        model.addAttribute("benefice" ,benefices);
        model.addAttribute("total",Benefice.calculTotal(benefices));
        model.addAttribute("mois",mois);
        model.addAttribute("annee",String.valueOf(annee));
        return "benefice-vente";
    }
    @GetMapping("/details")
    public String details(Model model,@RequestParam(defaultValue = "") String mois,@RequestParam(defaultValue = "0") int annee)throws Exception{
        Vente[] ventes = new Vente().findQuery(dataSource.getConnection(),"\n" +
                "select * from v_vente where EXTRACT(month from date_vente)="+mois+" and EXTRACT(year from date_vente)="+annee);
        model.addAttribute("details",ventes);
        return "details-vente";

    }


}
