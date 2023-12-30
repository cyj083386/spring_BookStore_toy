<%--
  Created by IntelliJ IDEA.
  User: peter
  Date: 2023-12-18
  Time: AM 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" />
    <title>Welcome</title>
</head>
<body>
    <nav class="navbar navbar-expand navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="./home.jsp">Home</a>
        </div>
    </nav>
    <div class="jumbotron">
        <div class="container">
            <h1 class="display-3">${greeting}</h1>
        </div>
    </div>
    <div class="container">
        <div class="text-center">
            <h3>${strapline}</h3>
        </div>
    </div>
    <%-- 무조건 아래라는 의미.--%>
    <footer class="container">
        <hr>
        <p>&copy; WebMarket</p>
    </footer>


</body>
</html>
