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
  DisqueDure[] disquDeDures = (DisqueDure[]) request.getAttribute("disque");
  DisqueDure[] disqueDures = (DisqueDure[]) request.getAttribute("disque-laptop");

%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>disque-<%=laptop.getModels().getMarque().getNom()%> <%=laptop.getModels().getNom()%></title>
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
      <h1>Ajout disque</h1>
    </div>
    <div class="table-main" id="form-table">
      <div class="form-table">
        <form action="<%=request.getContextPath()%>/admin/laptop/disque" class="formulaire" method="get" modelAttribute="laptop">
          <div class="form-group">
            <label for="">disque dure</label>
            <select name="disque" id="" class="class-form">
              <%for(DisqueDure disqueDure:disquDeDures){%>
              <option value="<%=disqueDure.getId()%>"><%=disqueDure.getTypeDisque().getNom()%> <%=disqueDure.getMemoire()%>Go</option>
              <%} %>
            </select>
            <%if(request.getAttribute("error-disque")!=null){%>
            <div class="error"><%=request.getAttribute("error-disque")%></div>
            <% } %>
          </div>
          <div class="form-group">
            <input type="submit" value="valider" />
          </div>
        </form>
      </div>
    </div>

    <div class="table-main">
      <h1>liste disque</h1>
    </div>
    <div class="table-main">
      <table>
        <tr>
          <th>disque-dure</th>
        </tr>
        <%for(DisqueDure disqueDure:disqueDures){%>
        <tr>
          <td><%=disqueDure.getTypeDisque().getNom()%> <%=disqueDure.getMemoire()%>Go</td>
          <td><a href="<%=request.getContextPath()%>/admin/laptop/suppDisque?id=<%=laptop.getId()%>&&disque=<%=disqueDure.getId()%>">supprimer</a> </td>
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

