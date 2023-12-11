<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Oracle
  Date: 16/05/2023
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <h1>points vente</h1>
  <sec:authorize access="isAuthenticated()">
      <form action="<%=request.getContextPath()%>/logout" method="post">
          <input type="submit" value="Logout">
      </form>
  </sec:authorize>
  <sec:authorize access="!isAuthenticated()">
      <p>login</p>
  </sec:authorize>
</body>
</html>
