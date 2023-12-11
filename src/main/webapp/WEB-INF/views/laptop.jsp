<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page import="com.example.ultraspringmvc.model.Models" %>
<%@ page import="com.example.ultraspringmvc.model.Processeur" %>
<%@ page import="org.springframework.security.access.method.P" %>
<%@ page import="com.example.ultraspringmvc.model.Ecran" %>
<%@ page import="com.example.ultraspringmvc.model.Ram" %><%--
  Created by IntelliJ IDEA.
  User: Oracle
  Date: 17/05/2023
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Models[] models = (Models[]) request.getAttribute("marque");
    Processeur[] processeurs = (Processeur[]) request.getAttribute("processeur");
    Ecran[] ecrans = (Ecran[]) request.getAttribute("ecran");
    Ram[] rams = (Ram[]) request.getAttribute("ram");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
    <title>ajout-laptop</title>
</head>
<style>
    .error{
        color: white;
        font-size:10px;
        background: red;
        height: 30%;
    }
</style>
<body>
<div class="header">
    <div class="header-div">
        <div class="icon-menu">
            <img class="icon" src="<c:url value="/resources/image/sliders-h-solid.svg"/>" alt=""/>
        </div>
        <div id="header-text"></div>
    </div>
</div>
<div class="content">
    <div class="menu" id="menu"></div>

    <div class="main">
        <div class="table-main">
            <h1>Ajout Laptop</h1>
        </div>
        <div class="table-main" id="form-table">
            <div class="form-table">
                <form action="<%=request.getContextPath()%>/admin/laptop/addLaptop" class="formulaire" method="post" modelAttribute="laptop">
                    <div class="form-group">
                        <label for="">marque</label>
                        <select name="models.id" id="" class="class-form">
                            <%for(Models models1:models){%>
                            <option value="<%=models1.getId()%>"><%=models1.getMarque().getNom()%> <%=models1.getNom()%></option>
                            <%} %>
                        </select>
                        <%if(request.getAttribute("error-models")!=null){%>
                        <div class="error"><%=request.getAttribute("error-models")%></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <label for="">processeur</label>
                        <select name="processeur.id" id="" class="class-form">
                            <%for(Processeur processeur:processeurs){%>
                            <option value="<%=processeur.getId()%>"><%=processeur.getTypeProcesseur().getNom()%> <%=processeur.getGeneration()%> <%=processeur.getFrequence()%>Go Hz </option>
                            <%} %>
                        </select>
                        <%if(request.getAttribute("error-processeur")!=null){%>
                        <div class="error"><%=request.getAttribute("error-processeur")%></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <label for="">ecran</label>
                        <select name="ecran.id" id="" class="class-form">
                            <%for(Ecran ecran:ecrans){%>
                            <option value="<%=ecran.getId()%>"><%=ecran.getValeur()%> pouce </option>
                            <%} %>
                        </select>
                        <%if(request.getAttribute("error-ecran")!=null){%>
                        <div class="error"><%=request.getAttribute("error-ecran")%></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <label for="">ram</label>
                        <select name="ram.id" id="" class="class-form">
                            <%for(Ram ram:rams){%>
                            <option value="<%=ram.getId()%>"><%=ram.getValeur()%> Go</option>
                            <%} %>
                        </select>
                        <%if(request.getAttribute("error-ram")!=null){%>
                        <div class="error"><%=request.getAttribute("error-ram")%></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <label for="">prix achat</label>
                        <input type="number" name="prix" placeholder="" id="" class="class-form" required/>
                        <%if(request.getAttribute("error-prix")!=null){%>
                        <div class="error"><%=request.getAttribute("error-prix")%></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <label for="">prix vente</label>
                        <input type="number" name="prixVente" placeholder="" id="" class="class-form" required/>
                        <%if(request.getAttribute("error-prixVente")!=null){%>
                        <div class="error"><%=request.getAttribute("error-prixVente")%></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <input type="submit" value="valider" />
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
    active("nouveaulaptop");
    <%if(request.getAttribute("reussi") !=null){%>
    alert("<%=request.getAttribute("reussi")%>")
    <%} %>
</script>
</body>
</html>

