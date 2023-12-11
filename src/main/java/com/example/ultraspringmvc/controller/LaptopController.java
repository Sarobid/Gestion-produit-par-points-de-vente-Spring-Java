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
@RequestMapping("/admin/laptop")
public class LaptopController {
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
                value = "redirect:/admin/" + e.getFunction();
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

    @GetMapping("/suppDisque")
    public String suppDisque(@ModelAttribute Laptop laptop,@RequestParam(defaultValue = "1") int id,@RequestParam(defaultValue = "0") int disque)throws Exception{
        Laptop laptop1 = laptop.findById(dataSource.getConnection(),"v_laptop",id);
        DisqueDure disqueDure = new DisqueDure().findById(dataSource.getConnection(),"v_disque",disque);
        laptop1.supprimerDisqueDure(disqueDure);
        return "redirect:/admin/laptop/disque?id="+id+"&&disque="+disque;
    }
    @GetMapping("/disque")
    public String disque(@ModelAttribute Laptop laptop,@RequestParam(defaultValue = "1") int id,@RequestParam(defaultValue = "0") int disque, Model model) throws Exception{
        Laptop laptop1 = laptop.findById(dataSource.getConnection(),"v_laptop",id);
        model.addAttribute("laptop",laptop1);
        model.addAttribute("disque",new DisqueDure().findQuery(dataSource.getConnection(),"select * from v_disque where id_disque_dure not in (select id_disque_dure from v_disque_laptop where id_laptop="+id+")"));
        model.addAttribute("disque-laptop",new DisqueDure().findQuery(dataSource.getConnection(), " select * from v_disque_laptop where id_laptop="+id));
        if(disque > 0){
            laptop1.nouveauDisque(new DisqueDure().findById(dataSource.getConnection(),"v_disque",disque));
        }
        return "disque-laptop";
    }
    /*@GetMapping("/suppRam")
    public String suppRam(@RequestParam(defaultValue = "0") int id,@RequestParam(defaultValue = "0") int ram)throws Exception{
        Laptop.supprimerRam(id,ram);
        return "redirect:/admin/laptop/ram?id="+id;
    }

    @GetMapping("/ram")
    public String ram(@ModelAttribute Laptop laptop,@RequestParam(defaultValue = "1") int id,@RequestParam(defaultValue = "0") int ram, Model model) throws Exception{
        Laptop laptop1 = laptop.findById(dataSource.getConnection(),"v_laptop",id);
        model.addAttribute("laptop",laptop1);
        model.addAttribute("ram",new Ram().findQuery(dataSource.getConnection(),"select * from ram where id_ram not in(select id_ram from ram_laptop where id_laptop= "+id+")"));
        model.addAttribute("ram-laptop",new Ram().findQuery(dataSource.getConnection(), " select * from v_ram where id_laptop="+id));
        if(ram > 0){
            laptop1.nouveauRam(dataSource.getConnection(),ram);
        }
        return "ram-laptop";
    }*/
    @GetMapping("/laptops")
    public String listeLaptops(@ModelAttribute Laptop laptop, BindingResult result,@RequestParam(defaultValue = "1") int num, Model model, HttpServletRequest request)throws Exception{
        if(result.hasErrors()){}
        Laptop[] laptops = laptop.recherchePaginer(dataSource.getConnection(),"v_laptop"," and delete=0 order by id_laptop asc ",num);
        Page page = laptop.getPage();
        page.setUrl("/admin/laptop/laptops"+laptop.url(request.getQueryString())+"&num=");
        model.addAttribute("laptop",laptops);
        model.addAttribute("page",page);
        model.addAttribute("marque",new Models().findQuery(dataSource.getConnection(),"select * from v_marque"));
        model.addAttribute("processeur",new Processeur().findQuery(dataSource.getConnection(),"select * from v_processeur"));
        model.addAttribute("ecran",new Ecran().findQuery(dataSource.getConnection(),"select * from ecran"));
        model.addAttribute("ram",new Ram().findQuery(dataSource.getConnection(),"select * from v_ram"));
        return "liste-laptop";
    }
    @GetMapping("/")
    public String laptop(Model model)throws Exception{
        model.addAttribute("marque",new Models().findQuery(dataSource.getConnection(),"select * from v_marque"));
        model.addAttribute("processeur",new Processeur().findQuery(dataSource.getConnection(),"select * from v_processeur"));
        model.addAttribute("ecran",new Ecran().findQuery(dataSource.getConnection(),"select * from ecran"));
        model.addAttribute("ram",new Ram().findQuery(dataSource.getConnection(),"select * from v_ram"));
        return "laptop";
    }
    @PostMapping("/addLaptop")
    public String addLaptop(@ModelAttribute Laptop laptop, BindingResult result,RedirectAttributes redirectAttributes)throws Exception{
        if(result.hasErrors()){
            throw new BadRequestException("",result);
        }
        laptop.insert(dataSource.getConnection(),null);
        redirectAttributes.addFlashAttribute("reussi","succes");
        return "redirect:/admin/laptop/";
    }
    @PostMapping("/modifier")
    public String modifieLaptop(@ModelAttribute Laptop laptop, BindingResult result,RedirectAttributes redirectAttributes)throws Exception{
        if(result.hasErrors()){
            throw new BadRequestException("",result);
        }
        laptop.update(dataSource.getConnection());
        return "redirect:/admin/laptop/laptops";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(defaultValue = "0") int id,RedirectAttributes redirectAttributes)throws Exception{
        //String query = "UPDATE laptop set delete=1 where id_laptop="+id;
        Laptop laptop = new Laptop().findById(dataSource.getConnection(),"laptop",id);
        laptop.delete(null);
        return "redirect:/admin/laptop/laptops";
    }
}
