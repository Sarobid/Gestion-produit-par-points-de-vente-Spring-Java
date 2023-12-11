<%@ page import="com.example.ultraspringmvc.model.Statistique" %>
<%@ page import="com.google.gson.Gson" %><%--
  Created by IntelliJ IDEA.
  User: Oracle
  Date: 19/05/2023
  Time: 06:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Gson gson = new Gson();
    Statistique[] statistiques = (Statistique[]) request.getAttribute("benefice");
%><html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Benefice par mois</h1>
    <table>
        <tr>
            <th>mois</th>
            <th>benefice</th>
        </tr>
        <%for(Statistique statistique:statistiques){%>
        <tr>
            <td><%=statistique.getMois()%></td>
            <td><%=statistique.getBenefice()%></td>
        </tr>
        <%}%>
    </table>
</body>
</html>
