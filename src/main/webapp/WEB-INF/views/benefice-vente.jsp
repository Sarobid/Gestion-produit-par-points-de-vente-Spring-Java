<%@ page import="jdk.nashorn.internal.parser.JSONParser" %>
<%@ page import="jdk.nashorn.internal.ir.debug.JSONWriter" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.example.ultraspringmvc.model.Statistique" %>
<%@ page import="com.example.ultraspringmvc.model.Benefice" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.time.Month" %>
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
    Month[] months = Month.values();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
    <title>Statistique-Benefice</title>
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

    <div class="main" id="main">
        <div class="table-main">
            <h1>Statistique Benefice </h1>
        </div>
        <div class="table-main">
            <div class="table-main">
                <p><a href="<%=request.getContextPath()%>/pdf/beneficePdf?mois=<%=mois%>&&annee=<%=annee%>">pdf</a> </p>
            </div>
        </div>
        <div class="table-main" id="form-table">
            <div class="table-main">
                <div class="recherche">
                    <form action="<%=request.getContextPath()%>/admin/benefice/benefice" class="form" method="get" >
                        <div class="form-group">
                            <label for="">mois</label>
                            <select name="mois" id="" class="class-form">
                                <option value=""></option>
                                <%
                                    int i = 0;
                                    for(i = 0 ; i < months.length ; i++){%>
                                <option value="<%=i+1%>"><%=months[i].name()%></option>
                                <%} %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="">annee</label>
                            <input type="number" name="annee" value="0" placeholder="" id="" class="class-form"/>
                        </div>
                        <div class="form-group">
                            <input type="submit" value="recherche">
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="table-main">
            <table>
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
                    <td style="background-color:<%=Benefice.color(benefice.getVente())%>;"><a href="<%=request.getContextPath()%>/admin/benefice/details?mois=<%=benefice.getId_mois()%>&&annee=<%=benefice.getAnnee()%>" ><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getVente())%></a></td>
                    <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getSommeAchat())%></td>
                    <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getBeneficeBrute())%></td>
                    <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getPerte())%></td>
                    <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getBenefice())%></td>
                    <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getCommissionValue())%></td>
                    <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(benefice.getBeneficeCom())%></td>
                </tr>
                <%}%>
                <tr>
                    <td>TOTAL</td>
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
        </div>
        <div class="table-main">
            <div class="canvas">
                <canvas id="myChart"></canvas>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
    active("benefice-vente");
</script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    var dataset = <%=gson.toJson(benefices)%>;
    const data = {
        labels: dataset.map(row=>row.mois),
        datasets: [
            {
                label: 'vente',
                data: dataset.map(row=>row.vente),
                borderColor: '#FF0000',
                backgroundColor: '#FF0000',
                order: 1
            },
            {
                label: 'benefice',
                data: dataset.map(row=>row.beneficeCom),
                borderColor: '#00C034',
                backgroundColor: '#00C034',
                type: 'line',
                order: 0
            }
        ]
    };
    const config = {
        type: 'bar',
        data: data,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'statistique benefice'
                }
            }
        },
    };
    const ctx = document.getElementById('myChart');
    new Chart(ctx,config);
</script>
</body>
</html>
