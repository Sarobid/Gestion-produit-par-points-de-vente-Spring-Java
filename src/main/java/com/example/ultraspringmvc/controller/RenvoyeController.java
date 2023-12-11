package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.CustomUser;
import com.example.ultraspringmvc.exception.BadRequestException;
import com.example.ultraspringmvc.model.ReceptionTransfere;
import com.example.ultraspringmvc.model.Renvoye;
import com.example.ultraspringmvc.model.StockPoints;
import com.example.ultraspringmvc.proprety.SpringConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/user/renvoye")
public class RenvoyeController {
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
                value = "redirect:/user/renvoye/" + e.getFunction();
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
    public String renvoye(Model model)throws Exception{
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(customUser.getUtilisateur().getId_points_vente() == null){
            throw new Exception("vous n'etes pas affecte a aucun points de vente");
        }
        model.addAttribute("points-vente",customUser.getUtilisateur().getId_points_vente());
        model.addAttribute("stock",new StockPoints().findQuery(dataSource.getConnection(),"select * from v_stock_points where id_points_vente="+customUser.getUtilisateur().getId_points_vente().getId()));
        return "renvoye";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Renvoye renvoye, BindingResult result,@RequestParam(defaultValue = "0") int stock, RedirectAttributes redirectAttributes)throws Exception{
        if(result.hasErrors()){
            System.out.println(result);
        }
        renvoye.nouveauRenvoye(stock);

        return "redirect:/user/renvoye/";
    }



}
