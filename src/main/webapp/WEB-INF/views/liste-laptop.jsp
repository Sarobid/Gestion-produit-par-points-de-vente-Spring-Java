<%@ page import="com.example.ultraspringmvc.proprety.Page" %>
<%@ page import="com.example.ultraspringmvc.model.*" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Oracle
  Date: 14/04/2023
  Time: 06:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Page pages = (Page)request.getAttribute("page");
    Models[] models = (Models[]) request.getAttribute("marque");
    Processeur[] processeurs = (Processeur[]) request.getAttribute("processeur");
    Ecran[] ecrans = (Ecran[]) request.getAttribute("ecran");
    Laptop[] laptops = (Laptop[]) request.getAttribute("laptop");
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
    <title>liste laptop</title>
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
            <h1>liste laptop</h1>
        </div>
        <div class="table-main" id="form-table">
            <div class="table-main">
                <div class="recherche">
                    <form action="<%=request.getContextPath()%>/admin/laptop/laptops" class="form" method="get" modelAttribute="laptop" >
                        <div class="form-group">
                            <label for="">marque</label>
                            <select name="models.id" id="" class="class-form">
                                <option value="0"></option>
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
                                <option value="0"></option>
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
                                <option value="0"></option>
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
                                <option value="0"></option>
                                <%for(Ram ram:rams){%>
                                <option value="<%=ram.getId()%>"><%=ram.getValeur()%> Go</option>
                                <%} %>
                            </select>
                            <%if(request.getAttribute("error-ram")!=null){%>
                            <div class="error"><%=request.getAttribute("error-ram")%></div>
                            <% } %>
                        </div>
                        <div class="form-group">
                            <input type="submit" value="recherche"/>
                        </div>
                    </form>
                </div>
            </div>
            <div class="table-main">
                <table>
                    <tr>
                        <th>marque</th>
                        <th>processeur</th>
                        <th>ecran</th>
                        <th>ram</th>
                        <th>prix achat</th>
                        <th>prix vente</th>
                    </tr>
                    <%for (Laptop laptop:laptops){%>
                    <tr>
                        <form action="<%=request.getContextPath()%>/admin/laptop/modifier" method="post" modelAttribute="laptop">
                            <input type="hidden" name="id" value="<%=laptop.getId()%>"/>
                            <td>
                                <select name="models.id" id="">
                                <option value="<%=laptop.getModels().getId()%>"><%=laptop.getModels().getMarque().getNom()%> <%=laptop.getModels().getNom()%></option>
                                <%for(Models models1:models){%>
                                <option value="<%=models1.getId()%>"><%=models1.getMarque().getNom()%> <%=models1.getNom()%></option>
                                <%} %>
                            </select>
                            </td>
                            <td>
                            <select name="processeur.id" id="">
                                <option value="<%=laptop.getProcesseur().getId()%>"><%=laptop.getProcesseur().getTypeProcesseur().getNom()%> <%=laptop.getProcesseur().getGeneration()%> <%=laptop.getProcesseur().getGeneration()%> Go Hz</option>
                                <%for(Processeur processeur:processeurs){%>
                                <option value="<%=processeur.getId()%>"><%=processeur.getTypeProcesseur().getNom()%> <%=processeur.getGeneration()%> <%=processeur.getFrequence()%>Go Hz </option>
                                <%} %>
                            </select>
                            </td>
                            <td>
                            <select name="ecran.id" id="">
                                <option value="<%=laptop.getEcran().getId()%>"><%=laptop.getEcran().getValeur()%> pouce</option>
                                <%for(Ecran ecran:ecrans){%>
                                <option value="<%=ecran.getId()%>"><%=ecran.getValeur()%> pouce </option>
                                <%} %>
                            </select>
                            </td>
                            <td>
                            <select name="ram.id" id="">
                                <option value="<%=laptop.getRam().getId()%>"><%=laptop.getRam().getValeur()%> Go</option>
                                <%for(Ram ram:rams){%>
                                <option value="<%=ram.getId()%>"><%=ram.getValeur()%> Go</option>
                                <%} %>
                            </select>
                            </td>
                            <td><input type="number" name="prix" value="<%=laptop.getPrix()%>"/></td>
                            <td><input type="number" name="prixVente" value="<%=laptop.getPrixVente()%>"/></td>
                            <td><button type="submit">modifier</button> </td>
                        </form>
                        <td><a href="<%=request.getContextPath()%>/admin/laptop/delete?id=<%=laptop.getId()%>"> <button type="button" class="supprimer">Supprimer</button></a></td>
                        <td><a href="<%=request.getContextPath()%>/admin/laptop/disque?id=<%=laptop.getId()%>">disque</a></td>
                    </tr>
                    <%}%>
                </table>
            </div>
            <div class="table-main">
                <div class="pagination">
                    <a href="<%=request.getContextPath()%><%=pages.getUrl()%><%=pages.getNumero()-1%>" class="prev">Précédent</a>
                    <%if(pages.getNumero() < pages.getTotalPage()){%><a href="<%=request.getContextPath()%><%=pages.getUrl()%><%=pages.getNumero()+1%>" class="next">Suivant</a>
                    <%}%>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
    active("liste-laptop");
    <%if(request.getAttribute("reussi") !=null){%>
    alert("<%=request.getAttribute("reussi")%>")
    <%} %>
</script>
</body>
</html>
