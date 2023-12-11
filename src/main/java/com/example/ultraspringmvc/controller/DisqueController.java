package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.exception.BadRequestException;
import com.example.ultraspringmvc.model.DisqueDure;
import com.example.ultraspringmvc.model.Processeur;
import com.example.ultraspringmvc.model.TypeDisque;
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
@RequestMapping("/admin/disque")
public class DisqueController {
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
                value = "redirect:/admin/disque/" + e.getFunction();
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
    public String disque(Model model)throws Exception{
        model.addAttribute("disque",new TypeDisque().find(null,null));
        return "disque";
    }
    @PostMapping("/add")
    public String add(@ModelAttribute TypeDisque typeDisque, BindingResult result, RedirectAttributes redirectAttributes)throws  Exception{
        if(result.hasErrors()){
            throw new BadRequestException("",result);
        }
        typeDisque.insert(dataSource.getConnection(),null);
        redirectAttributes.addFlashAttribute("reussi","succes");
        return "redirect:/admin/disque/";
    }

    @PostMapping("/modifier")
    public String modifier(@ModelAttribute TypeDisque typeDisque,RedirectAttributes redirectAttributes)throws Exception{
        typeDisque.update(dataSource.getConnection());
        return "redirect:/admin/disque/";
    }
    @GetMapping("/disque")
    public String processeurDetails(Model model,@RequestParam(defaultValue = "0" )int id)throws Exception{
        TypeDisque typeDisque= new TypeDisque().findById(dataSource.getConnection(),"type_disque_dure",id);
        model.addAttribute("disque",typeDisque);
        model.addAttribute("liste",new DisqueDure().findQuery(dataSource.getConnection(),"select * from v_disque where id_type_disque_dure="+id));
        return "details-disque";
    }

    @PostMapping("/disque/add")
    public String addProDetails(@ModelAttribute DisqueDure disqueDure,BindingResult result,RedirectAttributes redirectAttributes)throws Exception{
        if(result.hasErrors()){throw new BadRequestException("disque",result);}
        disqueDure.insert(dataSource.getConnection(),null);
        return "redirect:/admin/disque/disque?id="+disqueDure.getTypeDisque().getId();
    }

    @PostMapping("/disque/modifier")
    public String modifierProcesseur(@ModelAttribute DisqueDure disqueDure)throws Exception{
        disqueDure.update(dataSource.getConnection());
        return "redirect:/admin/disque/disque?id="+disqueDure.getTypeDisque().getId();
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(defaultValue = "0") int id,RedirectAttributes redirectAttributes)throws Exception{
        TypeDisque processeur = new TypeDisque().findById(null,"type_disque_dure",id);
        processeur.delete(null);
        return "redirect:/admin/disque/";
    }
    @GetMapping("/disque/delete")
    public String deletePro(@RequestParam(defaultValue = "0") int id,RedirectAttributes redirectAttributes)throws Exception{
        DisqueDure processeur = new DisqueDure().findById(null,"disque_dure",id);
        processeur.delete(null);
        return "redirect:/admin/disque/disque?id="+processeur.getTypeDisque().getId();
    }

}
