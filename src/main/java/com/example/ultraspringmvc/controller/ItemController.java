package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.exception.BadRequestException;

import com.example.ultraspringmvc.proprety.SpringConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Controller
@Service
public class ItemController {
    @ExceptionHandler(Exception.class)
    public String handleItemException(RedirectAttributes redirectAttributes,Model model, Exception ex) {
        String value = "error";
        List<FieldError> errors = null;
        BadRequestException e = null;
        if(ex instanceof BadRequestException){
            e = (BadRequestException) ex;
            if(e.getFunction() != null){
                errors = e.getBindingResult().getFieldErrors();
                for(FieldError error:errors){
                    redirectAttributes.addFlashAttribute("error-"+error.getField(),error.getField()+" "+error.getDefaultMessage().substring(error.getDefaultMessage().indexOf(":")));
                }
                value="redirect:/"+e.getFunction();
            }else{
                model.addAttribute("error",e.getBindingResult().getFieldError().getDefaultMessage().substring(e.getBindingResult().getFieldError().getDefaultMessage().indexOf(":")+1));
            }
        }else{
            model.addAttribute("error",ex.getLocalizedMessage());
        }

        return value;
    }

    @Autowired
    private DataSource dataSource;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalTime.class,new SpringConvert(LocalTime.class));
    }
    @GetMapping("**/navbar")
    public String navbar(Model model){
        return "common/navbar";
    }
}


