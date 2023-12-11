package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.CustomUser;
import com.example.ultraspringmvc.exception.BadRequestException;
import com.example.ultraspringmvc.model.*;
import com.example.ultraspringmvc.proprety.SpringConvert;
import com.example.ultraspringmvc.sql.Column;
import lombok.Getter;
import lombok.Setter;
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
@RequestMapping("/admin/renvoye")
public class ReceptionMagasinController {
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
                value = "redirect:/admin/renvoye/" + e.getFunction();
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
        model.addAttribute("etat",new Etat().find(null,null));
        model.addAttribute("renvoye", new Renvoye().findQuery(dataSource.getConnection(), "select * from v_renv_non_recu"));
        return "reception-renvoye";
    }

    @GetMapping("/liste")
    public String liste(Model model) throws Exception {
        model.addAttribute("renvoye", new ReceptionRenvoye().listeAll());
        return "liste-recepte-renvoye";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute ReceptionRenvoye reception, RedirectAttributes redirectAttributes)throws Exception{
        reception.nouveauReception();
        redirectAttributes.addFlashAttribute("reussi","veuiller regarder votre stock");
        return "redirect:/admin/renvoye/";
    }

    @PostMapping("/modification")
    public String modiication(@ModelAttribute ReceptionRenvoye reception, RedirectAttributes redirectAttributes)throws Exception{
        reception.modificationReception();
        return "redirect:/admin/renvoye/liste";
    }
    @GetMapping("/supprimer")
    public String supprimer(@ModelAttribute ReceptionRenvoye reception,@RequestParam(defaultValue = "0") int id, RedirectAttributes redirectAttributes)throws Exception{
        reception.supprimer(id);
        return "redirect:/admin/renvoye/liste";
    }

    @GetMapping("/annuler")
    public String annuler(@ModelAttribute Renvoye renvoye,@RequestParam(defaultValue = "0")int id)throws Exception{
        renvoye.supprimer(id);
        return "redirect:/admin/renvoye/";
    }

}
