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
  Laptop laptop = (Laptop) request.getAttribute("laptop");
  Ram[] rams = (Ram[]) request.getAttribute("ram");
  Ram[] ramse = (Ram[]) request.getAttribute("ram-laptop");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>ram-<%=laptop.getModels().getMarque()%> <%=laptop.getModels().getNom()%></title>
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
      <h1><%=laptop.getModels().getMarque().getNom()%> <%=laptop.getModels().getNom()%></h1>
    </div>
    <div class="table-main">
      <div class="grid-container-1">
        <div class="grid-item">
          <div class="grid-item-sous">
            processeur : <%=laptop.getProcesseur().getTypeProcesseur().getNom() %><%=laptop.getProcesseur().getGeneration()%> <%=laptop.getProcesseur().getFrequence()%> Go Hz
          </div>
          <div class="grid-item-sous">
            ecran : <%=laptop.getEcran().getValeur()%> pouce
          </div>
        </div>
      </div>
    </div>
    <div class="table-main">
      <h1>Ajout ram</h1>
    </div>
    <div class="table-main" id="form-table">
      <div class="form-table">
        <form action="<%=request.getContextPath()%>/admin/laptop/ram" class="formulaire" method="get" modelAttribute="laptop">
          <div class="form-group">
            <label for="">ram</label>
            <select name="ram" id="" class="class-form">
              <%for(Ram ram:rams){%>
              <option value="<%=ram.getId()%>"><%=ram.getValeur()%> Go</option>
              <%} %>
            </select>
            <%if(request.getAttribute("error-ram")!=null){%>
            <div class="error"><%=request.getAttribute("error-ram")%></div>
            <% } %>
          </div>
          <div class="form-group">
            <input type="submit" value="valider" />
          </div>
        </form>
      </div>
    </div>

    <div class="table-main">
      <h1>liste ram</h1>
    </div>
    <div class="table-main">
      <table>
        <tr>
          <th>ram</th>
        </tr>
        <%for(Ram ram:ramse){%>
        <tr>
          <td><%=ram.getValeur()%> Go</td>
          <td><a href="<%=request.getContextPath()%>/admin/laptop/suppRam?id=<%=laptop.getId()%>&&ram=<%=ram.getId()%>">supprimer</a> </td>
        </tr>
        <%}%>
      </table>
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

