<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2017/12/21
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h4>Hello World</h4>
    <br>
    time:${requestScope.time}
    <br>
    names:${requestScope.names}
    <br>
    user:${requestScope.user}
    <br>
    user:${sessionScope.user}
</body>
</html>
