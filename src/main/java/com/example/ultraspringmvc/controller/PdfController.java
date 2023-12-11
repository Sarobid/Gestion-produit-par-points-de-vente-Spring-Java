package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.model.Benefice;
import com.example.ultraspringmvc.model.Commission;
import com.example.ultraspringmvc.model.Statistique;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.sql.DataSource;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;

@CrossOrigin
@Controller
@Service
@RequestMapping("/pdf")
public class PdfController {
    @Autowired
    DataSource dataSource;
    public void generatePdfFromModelAndView(String url,HttpServletRequest request,HttpServletResponse response) throws Exception {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        Document document = new Document();
        RestTemplate restTemplate = new RestTemplate();
        PdfWriter writer = PdfWriter.getInstance(document, byteArray);
        document.open();
        System.out.println(url);
        String path = restTemplate.getForObject(url,String.class);
        System.out.println(path);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(path.getBytes());
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputStream);
        document.close();
        response.setHeader("Content-Disposition", "attachment; filename=benefice.pdf");
        response.setContentType("application/pdf");
        response.setContentLength(byteArray.size());
        byteArray.writeTo(response.getOutputStream());
        byteArray.flush();
        byteArray.close();
    }
    @GetMapping("/pdf")
    public void generatePdfModelAndView(@RequestParam(defaultValue = "") String url,HttpServletRequest request,HttpServletResponse response) throws Exception {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        Document document = new Document();
        RestTemplate restTemplate = new RestTemplate();
        PdfWriter writer = PdfWriter.getInstance(document, byteArray);
        document.open();
        System.out.println(url);
        String path = restTemplate.getForObject("http://localhost:8080/"+request.getContextPath()+"/"+url,String.class);
        System.out.println(path);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(path.getBytes());
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputStream);
        document.close();
        response.setHeader("Content-Disposition", "attachment; filename=benefice.pdf");
        response.setContentType("application/pdf");
        response.setContentLength(byteArray.size());
        byteArray.writeTo(response.getOutputStream());
        byteArray.flush();
        byteArray.close();
    }

    @GetMapping("/benefice")
    public String benefice(@ModelAttribute Benefice benefice, Model model, @RequestParam(defaultValue = "") String mois, @RequestParam(defaultValue = "0")int annee)throws Exception{
        Benefice[] benefices = benefice.listeBenefice(mois,annee);
        benefice.listeBeneficeAvecCommission(benefices, Commission.listeAll());
        model.addAttribute("benefice" ,benefices);
        model.addAttribute("total",Benefice.calculTotal(benefices));
        return "pdf/benefice-vente";
    }

    @GetMapping("/vente")
    public String state(Model model,@RequestParam(defaultValue = "") String mois,@RequestParam(defaultValue = "0") int annee)throws Exception{
        Statistique statistique = new Statistique();
        model.addAttribute("vente-globale",statistique.venteParMoisGlobal(mois,annee));
        //model.addAttribute("vente-points-vente",statistique.venteParMoisParPointsVente(mois,annee));
        return "pdf/statistique";
    }
    @GetMapping("/beneficePdf")
    public void exportPdf(@RequestParam(defaultValue = "") String mois, @RequestParam(defaultValue = "0")int annee,HttpServletRequest request, HttpServletResponse response)throws Exception{
        try {
            generatePdfFromModelAndView("http://localhost:8080/"+request.getContextPath()+"/pdf/benefice?mois="+mois+"&&annee="+annee,request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/pdf/benefice")
    public String benefice(Model model,@RequestParam(defaultValue = "0") int annee)throws Exception{
        Statistique statistique = new Statistique();
        model.addAttribute("benefice",statistique.beneficeParMois(annee));
        return "pdf/benefice";
    }


}
