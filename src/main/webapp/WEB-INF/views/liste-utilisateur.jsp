
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
    Utilisateur[] utilisateurs = (Utilisateur[]) request.getAttribute("utilisateur");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
    <title>liste utilisateur</title>
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
            <h1>liste utilisateur</h1>
        </div>
        <div class="table-main" id="form-table">
            <div class="table-main">
                <div class="recherche">
                    <form action="<%=request.getContextPath()%>/admin/utilisateur/liste" class="form" method="get" modelAttribute="utilisateur" >
                        <div class="form-group">
                            <label for="">Nom</label>
                            <input type="text" name="nom" placeholder="" id="" class="class-form" required/>
                            <%if(request.getAttribute("error-nom")!=null){%>
                            <div class="error"><%=request.getAttribute("error-nom")%></div>
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
                        <th>nom</th>
                    </tr>
                    <%for (Utilisateur utilisateur:utilisateurs){%>
                    <tr>
                        <td><%=utilisateur.getNom()%></td>
                        <td><a href="<%=request.getContextPath()%>/admin/utilisateur/affectation?id=<%=utilisateur.getId()%>">affectation</a> </td>
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
    active("liste-utilisateur");
    <%if(request.getAttribute("reussi") !=null){%>
    alert("<%=request.getAttribute("reussi")%>")
    <%} %>
</script>
</body>
</html>
