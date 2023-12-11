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
  Commission[] commissions = (Commission[]) request.getAttribute("commission");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>ecran</title>
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
      <h1>Nouveau Commission</h1>
    </div>
    <div class="table-main" id="form-table">
      <div class="form-table">
        <form action="<%=request.getContextPath()%>/admin/commission/add" class="formulaire" method="post" modelAttribute="ram">
          <div class="form-group">
            <label for="">total max</label>
            <input type="number" name="max" placeholder="" id="" class="class-form"/>
            <%if(request.getAttribute("error-max")!=null){%>
            <div class="error"><%=request.getAttribute("error-max")%></div>
            <% } %>
          </div>
          <div class="form-group">
            <label for="">commission</label>
            <input type="number" name="pourcentage" placeholder="" id="" class="class-form"/>
            <%if(request.getAttribute("error-pourcentage")!=null){%>
            <div class="error"><%=request.getAttribute("error-pourcentage")%></div>
            <% } %>
          </div>
          <div class="form-group">
            <input type="submit" value="valider" />
          </div>
        </form>
      </div>
    </div>

    <div class="table-main">
      <h1>liste Ram</h1>
    </div>
    <div class="table-main">
      <table>
        <tr>
          <th>Total Min</th>
          <th>Total Max</th>
          <th>commission</th>
        </tr>
        <%for(Commission commission:commissions){%>
        <tr>
          <form method="post" action="<%=request.getContextPath()%>/admin/commission/modifier" modelAttribute="ram">
            <input type="hidden" name="id" value="<%=commission.getId()%>"/>
            <td><input type="number" name="min" value="<%=commission.getMin()%>"/></td>
            <td><input type="number" name="max" value="<%=commission.getMax()%>"/></td>
            <td><input type="number" name="pourcentage" value="<%=commission.getPourcentage()%>"/></td>
            <td><button type="submit">modifier</button> </td>
          </form>
          <td><a href="<%=request.getContextPath()%>/admin/commission/delete?id=<%=commission.getId()%>"><button type="button" class="supprimer">supprimer</button> </a> </td>
        </tr>
        <%}%>
      </table>
    </div>
  </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
  active("commission");
  <%if(request.getAttribute("reussi") !=null){%>
  alert("<%=request.getAttribute("reussi")%>")
  <%} %>
</script>
</body>
</html>

