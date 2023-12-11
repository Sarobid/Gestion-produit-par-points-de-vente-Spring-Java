package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.exception.BadRequestException;
import com.example.ultraspringmvc.model.Marque;
import com.example.ultraspringmvc.model.Models;
import com.example.ultraspringmvc.model.PointsVente;
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
@RequestMapping("/admin/marque")
public class MarqueController {
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
                    redirectAttributes.addFlashAttribute(e.getName(),e.getObject());
                }
                value = "redirect:/admin/marque/" + e.getFunction();
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

    @GetMapping("/models")
    public String models(Model model,@RequestParam(defaultValue = "0") int id)throws Exception{
        model.addAttribute("marque",new Marque().findById(dataSource.getConnection(),"marque",id));
        model.addAttribute("model",new Models().findQuery(dataSource.getConnection(),"select * from v_marque where id_marque="+id));
        return "models";
    }
    @PostMapping("/addModels")
    public String addModels(@ModelAttribute Models models,BindingResult result,RedirectAttributes redirectAttributes)throws Exception{
        if(result.hasErrors()){
            throw new BadRequestException("models",result);
        }
        models.insert(dataSource.getConnection(),null);
        return "redirect:/admin/marque/models?id="+models.getMarque().getId();
    }
    @PostMapping("/models/modifier")
    public String modifierModels(@ModelAttribute Models models,RedirectAttributes redirectAttributes)throws Exception{
        models.update(dataSource.getConnection());
        return "redirect:/admin/marque/models?id="+models.getMarque().getId();
    }
    @GetMapping("/models/delete")
    public String deleteModel(@RequestParam(defaultValue = "0") int id,RedirectAttributes redirectAttributes)throws Exception{
        //String query = "UPDATE marque set delete=1 where id_marque="+id;
        Models pointsVente = new Models().findById(dataSource.getConnection(),"model",id);
        pointsVente.delete(null);
        return "redirect:/admin/marque/models?id="+pointsVente.getMarque().getId();
    }
    @GetMapping("/")
    public String marque(Model model)throws Exception{
        model.addAttribute("marque",new Marque().findQuery(null,"select * from marque where delete=0"));
        return "marque";
    }
    @PostMapping("/addMarque")
    public String addMarque(@ModelAttribute Marque marque, BindingResult result,RedirectAttributes redirectAttributes)throws Exception{
        if(result.hasErrors()){
            throw new BadRequestException("",result);
        }
        marque.insert(dataSource.getConnection(),null);
        redirectAttributes.addFlashAttribute("reussi","succes");
        return "redirect:/admin/marque/";
    }
    @PostMapping("/modifier")
    public String modifier(@ModelAttribute Marque marque,RedirectAttributes redirectAttributes)throws Exception{
        marque.update(dataSource.getConnection());
        return "redirect:/admin/marque/";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(defaultValue = "0") int id,RedirectAttributes redirectAttributes)throws Exception{
        String query = "UPDATE marque set delete=1 where id_marque="+id;
        Marque pointsVente = new Marque().findById(dataSource.getConnection(),"marque",id);
        pointsVente.delete(null);
        return "redirect:/admin/marque/";
    }

}
