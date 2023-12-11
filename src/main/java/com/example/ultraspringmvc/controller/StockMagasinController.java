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
@RequestMapping("/admin/stock")
public class StockMagasinController {
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
                value = "redirect:/admin/stock/" + e.getFunction();
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
    @GetMapping("/liste")
    public String liste(@ModelAttribute StockMagasin stockMagasin, BindingResult result,@RequestParam(defaultValue = "1") int num, Model model, HttpServletRequest request)throws Exception{
        if(result.hasErrors()){}
        StockMagasinFille stockMagasinFille = new StockMagasinFille();
        stockMagasinFille.setStockMagasin(stockMagasin);
        StockMagasinFille[] stockMagasinFilles = stockMagasinFille.recherchePaginer(dataSource.getConnection(),"v_stock_magasin_fille"," order by id_stock_magasin desc ",num);
        Page page = stockMagasinFille.getPage();
        page.setUrl("/admin/stock/liste"+stockMagasinFille.url(request.getQueryString())+"&num=");
        model.addAttribute("stock",stockMagasinFilles);
        model.addAttribute("page",page);
        model.addAttribute("laptop",new Laptop().findQuery(dataSource.getConnection(),"select * from v_laptop where delete=0"));
        return "liste-stock-magasin";
    }

    @GetMapping("/")
    public String stock(Model model)throws Exception{
        model.addAttribute("laptop",new Laptop().findQuery(dataSource.getConnection(),"select * from v_laptop where delete=0"));
        return "ajout-stock-magasin";
    }
    @PostMapping("/add")
    public String add(@ModelAttribute StockMagasin stockMagasin, BindingResult result,RedirectAttributes redirectAttributes)throws Exception{
        if(result.hasErrors()){throw new BadRequestException("",result);}
        stockMagasin.insert(dataSource.getConnection(),null);
        StockMagasinFille stockMagasinFille = new StockMagasinFille();
        stockMagasinFille.setStockMagasin(stockMagasin);
        stockMagasinFille.setQuantite(stockMagasin.getQuantite());
        stockMagasinFille.insert(null,null);
        redirectAttributes.addFlashAttribute("reussi","succes");
        return "redirect:/admin/stock/";
    }
    @PostMapping("/modifier")
    public String modifier(@ModelAttribute StockMagasin stockMagasin)throws Exception{
        stockMagasin.update(null);
        return "redirect:/admin/stock/liste";
    }
    @GetMapping("/supprimer")
    public String supprimer(@ModelAttribute StockMagasinFille stockMagasinFille,@RequestParam(defaultValue = "0")int id)throws Exception{
        stockMagasinFille = stockMagasinFille.findById(null,"v_stock_magasin_fille",id);
        StockMagasin stockMagasin = stockMagasinFille.getStockMagasin();
        stockMagasinFille.delete(null);
        stockMagasin.delete(null);
        return "redirect:/admin/stock/liste";
    }
}
