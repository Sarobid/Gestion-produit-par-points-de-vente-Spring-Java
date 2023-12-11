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
  TypeDisque[] typeDisques = (TypeDisque[]) request.getAttribute("disque");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>disque</title>
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
      <h1>Nouveau Disque Dure</h1>
    </div>
    <div class="table-main" id="form-table">
      <div class="form-table">
        <form action="<%=request.getContextPath()%>/admin/disque/add" class="formulaire" method="post" modelAttribute="typeDisque">
          <div class="form-group">
            <label for="">Nom</label>
            <input type="text" name="nom" placeholder="" id="" class="class-form"/>
            <%if(request.getAttribute("error-nom")!=null){%>
            <div class="error"><%=request.getAttribute("error-nom")%></div>
            <% } %>
          </div>
          <div class="form-group">
            <input type="submit" value="valider" />
          </div>
        </form>
      </div>
    </div>

    <div class="table-main">
      <h1>liste Disque dure</h1>
    </div>
    <div class="table-main">
      <table>
        <tr>
          <th>Nom</th>
        </tr>
        <%for(TypeDisque typeDisque:typeDisques){%>
        <tr>
          <form method="post" action="<%=request.getContextPath()%>/admin/disque/modifier" modelAttribute="typeDisque">
            <input type="hidden" name="id" value="<%=typeDisque.getId()%>"/>
            <td><input type="text" name="nom" value="<%=typeDisque.getNom()%>"/></td>
            <td><button type="submit">modifier</button> </td>
          </form>
          <td><a href="<%=request.getContextPath()%>/admin/disque/disque?id=<%=typeDisque.getId()%>">details</a> </td>
          <td><a href="<%=request.getContextPath()%>/admin/disque/delete?id=<%=typeDisque.getId()%>"><button type="button" class="supprimer">supprimer</button> </a> </td>

        </tr>
        <%}%>
      </table>
    </div>
  </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
  active("disque");
  <%if(request.getAttribute("reussi") !=null){%>
  alert("<%=request.getAttribute("reussi")%>")
  <%} %>
</script>
</body>
</html>

