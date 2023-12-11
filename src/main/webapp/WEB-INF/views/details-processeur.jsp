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
  TypeProcesseur typeProcesseur = (TypeProcesseur) request.getAttribute("processeur");
  Processeur[] processeurs = (Processeur[]) request.getAttribute("liste");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title><%=typeProcesseur.getNom()%></title>
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
      <h1><%=typeProcesseur.getNom()%></h1>
    </div>
    <div class="table-main">
      <h1>Nouveau Configuration</h1>
    </div>
    <div class="table-main" id="form-table">
      <div class="form-table">
        <form action="<%=request.getContextPath()%>/admin/processeur/processeur/add" class="formulaire" method="post" modelAttribute="processeur">
          <input type="hidden" name="typeProcesseur.id" value="<%=typeProcesseur.getId()%>"/>
          <div class="form-group">
            <label for="">generation</label>
            <input type="number" name="generation" placeholder="" id="" class="class-form"/>
            <%if(request.getAttribute("error-generation")!=null){%>
            <div class="error"><%=request.getAttribute("error-generation")%></div>
            <% } %>
          </div>
          <div class="form-group">
            <label for="">frequence</label>
            <input type="number" name="frequence" placeholder="" id="" class="class-form"/>
            <%if(request.getAttribute("error-frequence")!=null){%>
            <div class="error"><%=request.getAttribute("error-frequence")%></div>
            <% } %>
          </div>
          <div class="form-group">
            <input type="submit" value="valider" />
          </div>
        </form>
      </div>
    </div>

    <div class="table-main">
      <h1>liste Configuration</h1>
    </div>
    <div class="table-main">
      <table>
        <tr>
          <th>generation</th>
          <th>frequence</th>
        </tr>
        <%for(Processeur processeur:processeurs){%>
        <tr>
          <form method="post" action="<%=request.getContextPath()%>/admin/processeur/processeur/modifier" modelAttribute="processeur">
            <input type="hidden" name="typeProcesseur.id" value="<%=typeProcesseur.getId()%>"/>
            <input type="hidden" name="id" value="<%=processeur.getId()%>"/>
            <td><input type="number" name="generation" value="<%=processeur.getGeneration()%>"/></td>
            <td><input type="number" name="frequence" value="<%=processeur.getFrequence()%>"/></td>
            <td><button type="submit">modifier</button> </td>
          </form>
          <td><a href="<%=request.getContextPath()%>/admin/processeur/processeur/delete?id=<%=processeur.getId()%>"><button type="button" class="supprimer">supprimer</button> </a> </td>
        </tr>
        <%}%>
      </table>
    </div>
  </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
  active("processeur");
  <%if(request.getAttribute("reussi") !=null){%>
  alert("<%=request.getAttribute("reussi")%>")
  <%} %>
</script>
</body>
</html>