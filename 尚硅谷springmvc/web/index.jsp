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

  <!--
  自定义视图
  -->
  <a href="/springMvc/testView">Test View </a>
  <br><br>

  <!--
  视频，视频解析器
  -->
  <a href="/springMvc/testViewAndViewResolver">TestViewAndViewResolver</a>
  <br><br>



  <!--
    模拟修改操作：1，原始数值：1,Tom,123456,2631478675@qq.com,12
    2.密码不能被修改
    3.表单回显，模拟操作直接在表单上填写对应的属性值
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
  <br><br>




 <a href=" /springMvc/testSessionAttributes">testSessionAttributes</a>
 <br><br>


  <!--
  springMvc处理模型数据，方式二：目标方法的返回值为modlemap类型
  -->
  <a href="/springMvc/testMap">testMap</a>
  <br><br>

  <!--
  springMvc处理模型数据，方式一：目标方法的返回值为modleandview类型
  -->
  <a href="/springMvc/testModelAndView">testModelAndView</a>
  <br><br>









  <!--
  使用servlet原生的api作为目标方法的参数
  -->
  <a href="/springMvc/testServletAPI">testServletAPI</a>
  <br><br>


  <!--
    POJO的使用
  -->
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
  <br><br>



  <!--
  @CookieValue的使用：
  -->
  <a href="/springMvc/testCookieValue">Test CookieValue</a>
  <br><br>



  <!--
  @RequestMapping传送请求参数，使用的是@RequestParam
  -->
  <a href="/springMvc/testRequestParam?username=liufei&age=12">Test RequestParam</a>
<br><br>





  <!--
  REST的使用：将post请求转化为delete，put请求(但好像不管用)
  -->
  <form action="/springMvc/testRestPut/1" method="post">
    <input type="hidden" name="_method" value="PUT"/>
    <input type="submit" value="TestRest PUT"/>
  </form>
  <br><br>
  <form action="/springMvc/testRest/1" method="post">
    <input type="hidden" name="_method" value="DELETE"/>
    <input type="submit" value="TestRest DELETE"/>
  </form>
  <br><br>
  <form action="/springMvc/testRest" method="post">
      <input type="submit" value="TestRest POST"/>
  </form>
  <br><br>
  <a href="/springMvc/testRest/1">Test Rest Get</a>
  <br><br>



  <!--
  @PathVariable的使用
  -->
  <a href="/springMvc/testPathVariable/1">Test PathVariable</a>




  <!--
  @RequestMapping的使用
  -->
  <!-- 对应params对应请求参数，必须有username，且年龄不为10-->
  <a href="/springMvc/testParams?username=liuyuanlin&age=12">Test Params</a>
  <!-- 对应method对应请求方法-->
  <form action="/springMvc/testMethod" method="post">
    <input type="submit" value="submit">
  </form>
  <a href="/springMvc/testMethod">222</a>
  <br><br>
  <a href="/springMvc/test"></a>
  <br><br>
  <a href="helloWorld">Hello World</a>
  </body>
</html>
