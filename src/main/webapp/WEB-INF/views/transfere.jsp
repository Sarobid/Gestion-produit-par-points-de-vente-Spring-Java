<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page import="org.springframework.security.access.method.P" %>
<%@ page import="com.example.ultraspringmvc.model.*" %><%--
  Created by IntelliJ IDEA.
  User: Oracle
  Date: 17/05/2023
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    StockMagasinFille stockMagasinFille = (StockMagasinFille) request.getAttribute("stock");
    StockMagasin stockMagasin = stockMagasinFille.getStockMagasin();
    PointsVente[] pointsVentes = (PointsVente[]) request.getAttribute("points-vente");
    Transfere[] transferes = (Transfere[]) request.getAttribute("transfere");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
    <title>transfere-<%=stockMagasin.getLaptop().affiche()%></title>
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
            <div class="grid-container-1">
                <div class="grid-item">
                    <div class="grid-item-sous">
                        laptop : <%=stockMagasin.getLaptop().affiche()%>
                    </div>
                    <div class="grid-item-sous">
                        quantite : <%=stockMagasinFille.getQuantite()%>
                    </div>
                </div>
            </div>
        </div>
        <div class="table-main">
            <h1>Nouveau Transfere</h1>
        </div>
        <div class="table-main" id="form-table">
            <div class="form-table">
                <form action="<%=request.getContextPath()%>/admin/transfere/add" class="formulaire" method="post" modelAttribute="transfere">
                    <input type="hidden" name="stockMagasin.id" value="<%=stockMagasin.getId()%>"/>
                    <input type="hidden" name="stockMagasin.quantite" value="<%=stockMagasin.getQuantite()%>"/>
                    <div class="form-group">
                        <label for="">date transfere</label>
                        <input type="date" name="date" placeholder="" id="" class="class-form"/>
                        <%if(request.getAttribute("error-date")!=null){%>
                        <div class="error"><%=request.getAttribute("error-date")%></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <label for="">quantite transferer</label>
                        <input type="number" name="quantite" placeholder="" id="" class="class-form"/>
                        <%if(request.getAttribute("error-quantite")!=null){%>
                        <div class="error"><%=request.getAttribute("error-quantite")%></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <label for="">Points de Vente</label>
                        <select name="pointsVente.id" id="" class="class-form">
                            <%for(PointsVente pointsVente:pointsVentes){%>
                            <option value="<%=pointsVente.getId()%>"><%=pointsVente.getNom()%></option>
                            <%} %>
                        </select>
                        <%if(request.getAttribute("error-pointsVente.id")!=null){%>
                        <div class="error"><%=request.getAttribute("error-pointsVente.id")%></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <input type="submit" value="valider" />
                    </div>
                </form>
            </div>
        </div>

        <div class="table-main">
            <h1>liste transfere</h1>
        </div>
        <div class="table-main">
            <table>
                <tr>
                    <th>date transfere</th>
                    <th>quantite transfere</th>
                    <th>points vente</th>
                </tr>
                <%for(Transfere transfere:transferes){%>
                <tr>
                    <form action="<%=request.getContextPath()%>/admin/transfere/modifier" method="post" modelAttribute="transfere">
                        <input type="hidden" name="id" value="<%=transfere.getId()%>"/>
                        <td><input type="date" name="date" value="<%=transfere.getDate()%>" /></td>
                        <td><input type="number" name="quantite" value="<%=transfere.getQuantite()%>"/></td>
                        <td>
                            <select name="pointsVente.id">
                                <option value="<%=transfere.getPointsVente().getId()%>"><%=transfere.getPointsVente().getNom()%></option>
                                <%for(PointsVente pointsVente:pointsVentes){%>
                                <option value="<%=pointsVente.getId()%>"><%=pointsVente.getNom()%></option>
                                <%} %>
                            </select>
                        <td><button type="submit">modifier</button> </td>
                    </form>
                    <td><a href="<%=request.getContextPath()%>/admin/transfere/supprimer?id=<%=transfere.getId()%>"><button type="button" class="supprimer">supprimer</button> </a> </td>
                </tr>
                <%}%>
            </table>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
    active("liste-stock-magasin");
    <%if(request.getAttribute("reussi") !=null){%>
    alert("<%=request.getAttribute("reussi")%>")
    <%} %>
</script>
</body>
</html>

