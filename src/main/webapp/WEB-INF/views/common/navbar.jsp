<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.example.ultraspringmvc.CustomUser" %>
<%--
<sec:authorize access="isAuthenticated()">
    <!-- Code HTML pour utilisateur connecté -->
</sec:authorize>

  Created by IntelliJ IDEA.
  User: Oracle
  Date: 14/04/2023
  Time: 07:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- ... -->


<%
  Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
%>
<ul>
  <sec:authorize access="isAuthenticated()">
    <li><a href="<%=request.getContextPath()%>/logout" id="logout">deconnexion</a></li>
  </sec:authorize>
  <sec:authorize access="!isAuthenticated()">
    <li><a href="<%=request.getContextPath()%>/login" id="login">login</a></li>
  </sec:authorize>
  <sec:authorize access="hasRole('ROLE_ADMIN')">
    <li><span>Gestion Laptop</span></li>
    <li><a href="<%=request.getContextPath()%>/admin/laptop/" id="nouveaulaptop">ajout Laptop</a></li>
    <li><a href="<%=request.getContextPath()%>/admin/laptop/laptops" id="liste-laptop">liste laptop</a></li>
    <li><span>Gestion pointVente</span></li>
    <li><a href="<%=request.getContextPath()%>/admin/points/" id="points-vente">points vente</a> </li>
    <li><span>Gestion Marque</span></li>
    <li><a href="<%=request.getContextPath()%>/admin/marque/" id="marque">marque</a> </li>
    <li><span>Gestion Processeur</span></li>
    <li><a href="<%=request.getContextPath()%>/admin/processeur/" id="processeur">processeur</a> </li>
    <li><span>Gestion Ram</span></li>
    <li><a href="<%=request.getContextPath()%>/admin/ram/" id="ram">ram</a></li>
    <li><span>Gestion Disque</span></li>
    <li><a href="<%=request.getContextPath()%>/admin/disque/" id="disque">disque dure</a></li>
    <li><span>Gestion Ecran</span></li>
    <li><a href="<%=request.getContextPath()%>/admin/ecran/" id="ecran">ecran</a></li>
    <li><span>Gestion Utlisateur</span></li>
    <li><a href="<%=request.getContextPath()%>/admin/utilisateur/" id="utilisateur">nouveau utilisateur</a> </li>
    <li><a href="<%=request.getContextPath()%>/admin/utilisateur/liste" id="liste-utilisateur">liste utilisateur</a> </li>
    <li><span>Gestion Stock</span></li>
    <li><a href="<%=request.getContextPath()%>/admin/stock/" id="ajout-stock-magasin">ajout stock</a> </li>
    <li><a href="<%=request.getContextPath()%>/admin/stock/liste" id="liste-stock-magasin">liste stock</a> </li>
    <li><span>Statistique</span></li>
    <li><a href="<%=request.getContextPath()%>/admin/state/statistique" id="state-vente">vente global</a> </li>
    <li><a href="<%=request.getContextPath()%>/admin/benefice/benefice" id="benefice-vente">benefice</a> </li>
    <li><span>Gestion Retour</span></li>
    <li><a href="<%=request.getContextPath()%>/admin/renvoye/" id="renvoye-reception">reception</a> </li>
    <li><a href="<%=request.getContextPath()%>/admin/renvoye/liste" id="reception-renvoye">perte et recu renvoye</a> </li>
    <li><span>Gestion Commission</span></li>
    <li><a href="<%=request.getContextPath()%>/admin/commission/" id="commission">parametre</a> </li>
  </sec:authorize>

<sec:authorize access="hasRole('ROLE_USER')">
  <li><span>Gestion reception</span></li>
  <li><a href="<%=request.getContextPath()%>/user/reception/" id="reception">liste transfere</a></li>
  <li><span>Gestion Stock</span></li>
  <li><a href="<%=request.getContextPath()%>/user/renvoye/" id="renvoye">renvoye</a></li>
  <li><a href="<%=request.getContextPath()%>/user/perte/" id="reception-transfere">perte et recu</a></li>
  <li><a href="<%=request.getContextPath()%>/user/stock/" id="stock-points">vente et stock</a></li>
  <li><a href="<%=request.getContextPath()%>/user/stock/vente/liste" id="liste-vente">liste vente</a></li>

</sec:authorize>

</ul>
