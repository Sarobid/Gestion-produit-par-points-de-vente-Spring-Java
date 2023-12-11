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
  StockPoints stockPoints = (StockPoints) request.getAttribute("stock");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>vente-<%=stockPoints.getReceptionTransfere().getTransfere().getStockMagasin().getLaptop().affiche()%></title>
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
            laptop : <%=stockPoints.getReceptionTransfere().getTransfere().getStockMagasin().getLaptop().affiche()%>
          </div>
          <div class="grid-item-sous">
            quantite : <%=stockPoints.getQuantite()%>
          </div>
          </div>
      </div>
    </div>
    <div class="table-main">
      <h1>Vente</h1>
    </div>
    <div class="table-main" id="form-table">
      <div class="form-table">
        <form action="<%=request.getContextPath()%>/user/stock/vente/add" class="formulaire" method="post" modelAttribute="vente">
          <input type="hidden" name="stockPoints.id" value="<%=stockPoints.getId()%>"/>
          <div class="form-group">
            <label for="">date :</label>
            <input type="date" name="dates" placeholder="" id="" class="class-form"  />
            <%if(request.getAttribute("error-dates")!=null){%>
            <div class="error"><%=request.getAttribute("error-dates")%></div>
            <% } %>
          </div>
          <div class="form-group">
            <label for="">quantite :</label>
            <input type="number" name="quantite" placeholder="" id="" class="class-form"/>
            <%if(request.getAttribute("error-quantite")!=null){%>
            <div class="error"><%=request.getAttribute("error-quantite")%></div>
            <% } %>
          </div>
          <div class="form-group">
            <label for="">prix :</label>
            <input type="number" name="prix" placeholder="" id="" class="class-form" value="<%=stockPoints.getReceptionTransfere().getTransfere().getStockMagasin().getLaptop().getPrixVente()%>" disabled/>
            <%if(request.getAttribute("error-prix")!=null){%>
            <div class="error"><%=request.getAttribute("error-prix")%></div>
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
  active("stock-points");
  <%if(request.getAttribute("reussi") !=null){%>
  alert("<%=request.getAttribute("reussi")%>")
  <%} %>
</script>
</body>
</html>


