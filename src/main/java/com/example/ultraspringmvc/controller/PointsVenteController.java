package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.exception.BadRequestException;
import com.example.ultraspringmvc.model.Laptop;
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
@RequestMapping("/admin/points")
public class PointsVenteController {
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
                value = "redirect:/admin/points/" + e.getFunction();
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
    @GetMapping("/commission")
    public String commission(@ModelAttribute PointsVente pointsVente,Model model,@RequestParam(defaultValue = "")String mois,@RequestParam(defaultValue = "0")int annee)throws Exception{
        model.addAttribute("points-vente",pointsVente.listeCommission(mois,annee));
        return "commission-points";
    }

    @PostMapping("/modifier")
    public String modifier(@ModelAttribute PointsVente pointsVente,RedirectAttributes redirectAttributes)throws Exception{
        pointsVente.update(dataSource.getConnection());
        return "redirect:/admin/points/";
    }
    @GetMapping("/")
    public String pointsVente(Model model)throws Exception{
        model.addAttribute("points",new PointsVente().findQuery(dataSource.getConnection(),"select * from points_vente where delete=0"));
        return "ajout-points-vente";
    }
    @PostMapping("/addPoints")
    public String addPoints(@ModelAttribute PointsVente pointsVente, BindingResult result,RedirectAttributes redirectAttributes)throws Exception{
        if(result.hasErrors()){
            throw new BadRequestException("",result);
        }
        pointsVente.insert(dataSource.getConnection(),null);
        redirectAttributes.addFlashAttribute("reussi","reussi");
    return "redirect:/admin/points/";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(defaultValue = "0") int id,RedirectAttributes redirectAttributes)throws Exception{
        //String query = "UPDATE points_vente set delete=1 where id_points_vente="+id;
        PointsVente pointsVente = new PointsVente().findById(dataSource.getConnection(),"points_vente",id);
        pointsVente.delete(null);
        return "redirect:/admin/points/";
    }

}
