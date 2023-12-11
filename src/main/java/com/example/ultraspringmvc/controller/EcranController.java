package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.exception.BadRequestException;
import com.example.ultraspringmvc.model.Ecran;
import com.example.ultraspringmvc.model.Ram;
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
@RequestMapping("/admin/ecran")
public class EcranController {
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
                value = "redirect:/admin/ecran/" + e.getFunction();
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
    public String ram(Model model)throws Exception{
        model.addAttribute("ecran",new Ecran().findQuery(dataSource.getConnection(),"select * from ecran"));
        return "ecran";
    }
    @PostMapping("/add")
    public String add(@ModelAttribute Ecran ram, BindingResult bindingResult, RedirectAttributes redirectAttributes)throws Exception{
        if(bindingResult.hasErrors()){throw new BadRequestException("",bindingResult);}
        ram.insert(null,null);
        return "redirect:/admin/ecran/";
    }
    @PostMapping("/modifier")
    public String modifier(@ModelAttribute Ecran ram, BindingResult bindingResult,RedirectAttributes redirectAttributes)throws Exception{
        if(bindingResult.hasErrors()){throw new BadRequestException("",bindingResult);}
        ram.update(null);
        return "redirect:/admin/ecran/";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(defaultValue = "0") int id,RedirectAttributes redirectAttributes)throws Exception{
        Ecran ram = new Ecran().findById(null,"ecran",id);
        ram.delete(dataSource.getConnection());
        return "redirect:/admin/ecran/";
    }


}
