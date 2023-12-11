<%@ page import="java.time.Month" %>
<%@ page import="com.example.ultraspringmvc.model.Statistique" %><%--
  Created by IntelliJ IDEA.
  User: Oracle
  Date: 19/05/2023
  Time: 10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Statistique[] statistiques = (Statistique[]) request.getAttribute("vente-globale");

%>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Statistique global vente par mois</h1>
    <table border="1">
        <tr>
            <th>mois</th>
            <th>total vente</th>
        </tr>
        <%for(Statistique statistique:statistiques){%>
        <tr>
            <td><%=statistique.getMois()%></td>
            <td><%=statistique.getNombre()%></td>
        </tr>
        <%}%>
    </table>
</body>
</html>
