<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page import="com.example.ultraspringmvc.model.Models" %>
<%@ page import="com.example.ultraspringmvc.model.Processeur" %>
<%@ page import="org.springframework.security.access.method.P" %>
<%@ page import="com.example.ultraspringmvc.model.Ecran" %>
<%@ page import="com.example.ultraspringmvc.model.Laptop" %><%--
  Created by IntelliJ IDEA.
  User: Oracle
  Date: 17/05/2023
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Laptop[] laptops = (Laptop[]) request.getAttribute("laptop");

%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>ajout-stock-magasin</title>
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
      <h1>Ajout Laptop Stock Magasin</h1>
    </div>
    <div class="table-main" id="form-table">
      <div class="form-table">
        <form action="<%=request.getContextPath()%>/admin/stock/add" class="formulaire" method="post" modelAttribute="stockMagasin">
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
            <label for="">date</label>
            <input type="date" name="dateEntree" placeholder="" id="" class="class-form" required/>
            <%if(request.getAttribute("error-dateEntree")!=null){%>
            <div class="error"><%=request.getAttribute("error-dateEntree")%></div>
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
  active("ajout-stock-magasin");
  <%if(request.getAttribute("reussi") !=null){%>
  alert("<%=request.getAttribute("reussi")%>")
  <%} %>
</script>
</body>
</html>

