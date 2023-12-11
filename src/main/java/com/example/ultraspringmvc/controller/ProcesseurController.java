package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.exception.BadRequestException;
import com.example.ultraspringmvc.model.Processeur;
import com.example.ultraspringmvc.model.TypeProcesseur;
import com.example.ultraspringmvc.proprety.SpringConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/admin/processeur")
public class ProcesseurController {
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
                value = "redirect:/admin/processeur/" + e.getFunction();
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
    public String processeur(Model model)throws Exception{
        model.addAttribute("processeur",new TypeProcesseur().find(null,null));
        return "processeur";
    }
    @PostMapping("/addProcesseur")
    public String addProcesseur(@ModelAttribute TypeProcesseur typeProcesseur, BindingResult result,RedirectAttributes redirectAttributes)throws  Exception{
        if(result.hasErrors()){
            throw new BadRequestException("",result);
        }
        typeProcesseur.insert(dataSource.getConnection(),null);
        redirectAttributes.addFlashAttribute("reussi","succes");
        return "redirect:/admin/processeur/";
    }

    @PostMapping("/modifier")
    public String modifier(@ModelAttribute TypeProcesseur typeProcesseur,RedirectAttributes redirectAttributes)throws Exception{
        typeProcesseur.update(dataSource.getConnection());
        return "redirect:/admin/processeur/";
    }
    @GetMapping("/processeur")
    public String processeurDetails(Model model,@RequestParam(defaultValue = "0" )int id)throws Exception{
        TypeProcesseur typeProcesseur = new TypeProcesseur().findById(dataSource.getConnection(),"type_processeur",id);
        model.addAttribute("processeur",typeProcesseur);
        model.addAttribute("liste",new Processeur().findQuery(dataSource.getConnection(),"select * from v_processeur where id_type_processeur="+id));
        return "details-processeur";
    }

    @PostMapping("/processeur/add")
    public String addProDetails(@ModelAttribute Processeur processeur,BindingResult result,RedirectAttributes redirectAttributes)throws Exception{
        if(result.hasErrors()){throw new BadRequestException("processeur",result);}
        processeur.insert(dataSource.getConnection(),null);
        return "redirect:/admin/processeur/processeur?id="+processeur.getTypeProcesseur().getId();
    }

    @PostMapping("/processeur/modifier")
    public String modifierProcesseur(@ModelAttribute Processeur processeur)throws Exception{
        processeur.update(dataSource.getConnection());
        return "redirect:/admin/processeur/processeur?id="+processeur.getTypeProcesseur().getId();
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(defaultValue = "0") int id,RedirectAttributes redirectAttributes)throws Exception{
        TypeProcesseur processeur = new TypeProcesseur().findById(null,"type_processeur",id);
        processeur.delete(null);
        return "redirect:/admin/processeur/";
    }
    @GetMapping("/processeur/delete")
    public String deletePro(@RequestParam(defaultValue = "0") int id,RedirectAttributes redirectAttributes)throws Exception{
        Processeur processeur = new Processeur().findById(null,"processeur",id);
        processeur.delete(null);
        return "redirect:/admin/processeur/processeur?id="+processeur.getTypeProcesseur().getId();
    }

}
