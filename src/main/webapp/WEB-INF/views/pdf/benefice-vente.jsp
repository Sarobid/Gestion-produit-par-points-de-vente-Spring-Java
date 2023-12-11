<%@ page import="jdk.nashorn.internal.parser.JSONParser" %>
<%@ page import="jdk.nashorn.internal.ir.debug.JSONWriter" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.example.ultraspringmvc.model.Statistique" %>
<%@ page import="com.example.ultraspringmvc.model.Benefice" %>
<%@ page import="java.text.NumberFormat" %>
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
  Gson gson = new Gson();
  Benefice[] benefices = (Benefice[]) request.getAttribute("benefice");
  Benefice beneficeTotal = (Benefice)request.getAttribute("total");
  String annee = (String) request.getAttribute("annee");
  String mois = (String) request.getAttribute("mois");

%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Statistique-Benefice</title>
</head>
<body>
      <h1>Statistique Benefice </h1>
      <table border="1" style="font-size: x-small">
        <tr>
          <th>mois</th>
          <th>annee</th>
          <th>vente</th>
          <th>somme Achat</th>
          <th>benefice Brute</th>
          <th>perte</th>
          <th>benefice</th>
          <th>commission</th>
          <th>benefice apres Com</th>
        </tr>
        <%for(Benefice benefice:benefices){%>
        <tr>
          <td><%=benefice.getMois()%></td>
          <td><%=benefice.getAnnee()%></td>
          <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getVente())%></td>
          <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getSommeAchat())%></td>
          <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getBeneficeBrute())%></td>
          <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getPerte())%></td>
          <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getBenefice())%></td>
          <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getCommissionValue())%></td>
          <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getBeneficeCom())%></td>
        </tr>
        <%}%>
        <tr>
          <td></td>
          <td></td>
          <th><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(beneficeTotal.getVente())%></th>
          <th><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(beneficeTotal.getSommeAchat())%></th>
          <th><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(beneficeTotal.getBeneficeBrute())%></th>
          <th><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(beneficeTotal.getPerte())%></th>
          <th><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(beneficeTotal.getBenefice())%></th>
          <th><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(beneficeTotal.getCommissionValue())%></th>
          <th><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(beneficeTotal.getBeneficeCom())%></th>
        </tr>
      </table>
</body>
</html>
