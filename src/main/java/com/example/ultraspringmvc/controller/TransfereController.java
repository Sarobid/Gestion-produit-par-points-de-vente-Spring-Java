package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.exception.BadRequestException;
import com.example.ultraspringmvc.model.PointsVente;
import com.example.ultraspringmvc.model.StockMagasin;
import com.example.ultraspringmvc.model.StockMagasinFille;
import com.example.ultraspringmvc.model.Transfere;
import com.example.ultraspringmvc.proprety.SpringConvert;
import lombok.Getter;
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
@RequestMapping("/admin/transfere")
public class TransfereController {
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
                value = "redirect:/admin/transfere/" + e.getFunction();
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

    @GetMapping("")
    public String transfere(Model model, @RequestParam(defaultValue = "0") int id)throws Exception{
        StockMagasinFille stockMagasinFille = new StockMagasinFille().findById(dataSource.getConnection(),"v_stock_magasin_fille",id);
        model.addAttribute("stock",stockMagasinFille);
        model.addAttribute("transfere",new Transfere().findQuery(dataSource.getConnection(),"select * from v_transfere where id_stock_magasin="+stockMagasinFille.getStockMagasin().getId()));
        model.addAttribute("points-vente",new PointsVente().find(null,null));
        return "transfere";
    }
    @PostMapping("/add")
    public String add(@ModelAttribute Transfere transfere, BindingResult bindingResult,RedirectAttributes redirectAttributes) throws Exception{
        if(bindingResult.hasErrors()){throw new BadRequestException("",bindingResult);}
        transfere.nouveauTransfere();
        StockMagasinFille stockMagasinFille = (StockMagasinFille) new StockMagasinFille().findQuery(null,"select * from stock_magasin_fille where id_stock_magasin="+transfere.getStockMagasin().getId())[0];
        return "redirect:/admin/transfere?id="+stockMagasinFille.getId();
    }
    @PostMapping("/modifier")
    public String modifier(@ModelAttribute Transfere transfere)throws Exception{
        transfere.modifier();
        StockMagasinFille stockMagasinFille = (StockMagasinFille) new StockMagasinFille().findQuery(null,"select * from stock_magasin_fille where id_stock_magasin="+transfere.getStockMagasin().getId())[0];
        return "redirect:/admin/transfere?id="+stockMagasinFille.getId();
    }
    @GetMapping("/supprimer")
    public String supprimer(@ModelAttribute Transfere transfere,@RequestParam(defaultValue = "0") int id) throws Exception{
        transfere.supprimer(id);
        transfere  = transfere.findById(null,"transfere",id);
        StockMagasinFille stockMagasinFille = (StockMagasinFille) new StockMagasinFille().findQuery(null,"select * from stock_magasin_fille where id_stock_magasin="+transfere.getStockMagasin().getId())[0];
        return "redirect:/admin/transfere?id="+stockMagasinFille.getId();
    }

}
