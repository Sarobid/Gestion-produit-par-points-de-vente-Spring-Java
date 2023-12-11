
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
  Laptop[] laptops = (Laptop[]) request.getAttribute("laptop");
  StockMagasinFille[] stockMagasinFilles = (StockMagasinFille[]) request.getAttribute("stock");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>liste-stock-magasin</title>
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
      <h1>liste Stock Laptop au Magasin</h1>
    </div>
    <div class="table-main" id="form-table">
      <div class="table-main">
        <div class="recherche">
          <form action="<%=request.getContextPath()%>/admin/stock/liste" class="form" method="post" modelAttribute="stockMagasin">
            <div class="form-group">
              <label for="">Laptop</label>
              <select name="laptop.id" id="" class="class-form">
                <%for(Laptop laptop:laptops){%>
                <option value="<%=laptop.getId()%>"><%=laptop.affiche()%></option>
                <%} %>
              </select>
              <%if(request.getAttribute("error-laptop.id")!=null){%>
              <div class="error"><%=request.getAttribute("error-laptop.id")%></div>
              <% } %>
            </div>
            <div class="form-group">
              <label for="">quantite</label>
              <input type="number" name="quantite" placeholder="" id="" class="class-form" required/>
              <%if(request.getAttribute("error-quantite")!=null){%>
              <div class="error"><%=request.getAttribute("error-quantite")%></div>
              <% } %>
            </div>
            <div class="form-group">
              <input type="submit" value="recherche" />
            </div>
          </form>
        </div>
      </div>
      <div class="table-main">
        <table>
          <tr>
            <th>date entree</th>
            <th>Laptop</th>
            <th>quantite</th>
          </tr>
          <%for (StockMagasinFille stockMagasinFille:stockMagasinFilles){
            StockMagasin stockMagasin = stockMagasinFille.getStockMagasin();
          %>
          <tr>
            <form action="<%=request.getContextPath()%>/admin/stock/modifier" method="post" modelAttribute="stockMagasin">
              <input type="hidden" name="id" value="<%=stockMagasin.getId()%>"/>
              <td><input type="date" name="dateEntree" value="<%=stockMagasin.getDateEntree()%>"/></td>
              <td><%=stockMagasin.getLaptop().affiche()%></td>
              <td><%=stockMagasinFille.getQuantite()%></td>
              <td><button type="submit">modifier</button> </td>
            </form>
            <td><a href="<%=request.getContextPath()%>/admin/transfere?id=<%=stockMagasinFille.getId()%>">transfere</a></td>
            <td><a href="<%=request.getContextPath()%>/admin/stock/supprimer?id=<%=stockMagasinFille.getId()%>">supprimer</a> </td>
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
  active("liste-stock-magasin");
  <%if(request.getAttribute("reussi") !=null){%>
  alert("<%=request.getAttribute("reussi")%>")
  <%} %>
</script>
</body>
</html>
