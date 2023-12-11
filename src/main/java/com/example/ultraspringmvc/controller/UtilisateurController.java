package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.exception.BadRequestException;
import com.example.ultraspringmvc.model.*;
import com.example.ultraspringmvc.proprety.Page;
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

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@Service
@RequestMapping("/admin/utilisateur")
public class UtilisateurController {
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
                value = "redirect:/admin/utilisateur/" + e.getFunction();
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

    @GetMapping("/affectation")
    public String affectation(Model model,@RequestParam(defaultValue = "0") int id)throws Exception{
        model.addAttribute("utilisateur",new Utilisateur().findById(dataSource.getConnection(),"utilisateur",id));
        model.addAttribute("affectation",new Utilisateur().findQuery(dataSource.getConnection(),"select * from v_affectation where id_utilisateur="+id+" and date_fin is null"));
        model.addAttribute("points-vente",new PointsVente().findQuery(dataSource.getConnection(),"select * from points_vente where delete=0"));
        return "affectation";
    }
    @PostMapping("/desaffectation")
    public String desaffectation(@ModelAttribute Utilisateur utilisateur,RedirectAttributes redirectAttributes)throws Exception{
        utilisateur.desafectation();
        return "redirect:/admin/utilisateur/affectation?id="+utilisateur.getId();
    }
    @PostMapping("/affectation/add")
    public String addAffectation(@ModelAttribute Utilisateur utilisateur,BindingResult result,RedirectAttributes redirectAttributes)throws Exception{
        utilisateur.nouveauAffectation();
        return "redirect:/admin/utilisateur/affectation?id="+utilisateur.getId();
    }
    @GetMapping("/liste")
    public String liste(@ModelAttribute Utilisateur utilisateur,BindingResult result,@RequestParam(defaultValue = "1") int num, Model model, HttpServletRequest request)throws Exception{
        if(result.hasErrors()){}
        Utilisateur[] utilisateurs = utilisateur.recherchePaginer(dataSource.getConnection(),"utilisateur"," order by id_utilisateur desc ",num);
        Page page = utilisateur.getPage();
        page.setUrl("/admin/utilisateur/liste"+utilisateur.url(request.getQueryString())+"&num=");
        model.addAttribute("utilisateur",utilisateurs);
        model.addAttribute("page",page);
        return "liste-utilisateur";
    }

    @GetMapping("/")
    public String utilisateur(){
        return "utilisateur";
    }
    @PostMapping("/add")
    public String add(@ModelAttribute Compte compte, BindingResult result,@RequestParam(defaultValue = "0") String nom,RedirectAttributes redirectAttributes)throws Exception{
        if(result.hasErrors()){throw new BadRequestException("",result);}else {
            Utilisateur.nouveauUtilisateur(compte,nom);
            redirectAttributes.addFlashAttribute("reussi","succes");
        }
        return "redirect:/admin/utilisateur/";
    }
}
