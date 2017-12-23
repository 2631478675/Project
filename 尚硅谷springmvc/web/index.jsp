<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2017/12/21
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <a href="/springMvc/testViewResolver">testViewResolver</a>
  <!--
    模拟修改操作：1，原始数值：1,Tom,123456,2631478675@qq.com,12
    2.密码不能被修改
    3.
  -->
  <form action="/springMvc/testModelAttribute" method="post">
      <input type="hidden" name="id" value="1"/>
      username:<input type="text" name="username" value="Tom"/>
      <br>
      email:<input type="text" name="email" value="2631478675@qq.com"/>
      <br>
      age:<input type="text" name="age" value="12"/>
      <br>
      <input type="submit" value="Submit"/>
  </form>
  <br>
 <a href=" /springMvc/testSessionAttributes">testSessionAttributes</a>
 <br><br>
  <a href="/springMvc/testMap">testMap</a>
  <br>
  <a href="/springMvc/testModelAndView">testModelAndView</a>
  <br>
  <a href="/springMvc/testServletAPI">testServletAPI</a>
  <br>
  <form action="/springMvc/testPojo" method="post">
    username:<input type="text" name="username"/>
    <br>
    password:<input type="password" name="password"/>
    <br>
    email:<input type="text" name="email"/>
    <br>
    age:<input type="text" name="age"/>
    <br>
    city:<input type="text" name="adderss.city"/>
    <br>
    province:<input type="text" name="adderss.province"/>
    <br>
    <input type="submit" value="Submit"/>
  </form>
  <br>
  <a href="/springMvc/testCookieValue">testCookieValue</a>
  <br>
  <a href="/springMvc/testRequestParam?username=liufei&age=12">testRequestParam</a>
  <form action="/springMvc/testRest/1" method="post">
    <input type="hidden" name="_method" value="PUT"/>
    <input type="submit" value="TestRest PUT"/>
  </form>
  <br><br>
  <form action="/springMvc/testRest" method="post">
    <input type="hidden" name="_method" value="DELETE"/>
    <input type="submit" value="TestRest DELETE"/>
  </form>
  <br><br>
  <a href="/springMvc/testRest/1">Test get post</a>
  <a href="/springMvc/testParams?username=liuyuanlin&age=12">111</a>
  <form action="/springMvc/testMethod" method="post">
    <input type="submit" value="submit">
  </form>
  <a href="/springMvc/testMethod">222</a>
  <br><br>
  <a href="/springMvc/test"></a>
  <a href="helloWorld">Hello World</a>
  </body>
</html>
