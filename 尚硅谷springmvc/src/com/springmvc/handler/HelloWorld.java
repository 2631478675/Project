package com.springmvc.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
1.编写请求控制器
2.配置自动扫描的包
 */
@Controller
public class HelloWorld {
    /*
    使用@RequestMapping来映射url请求
    返回值会通过视图解析器解析为实际的物理视图，对于InternalResourceViewResolver视图解析器而言，
    会做如下的解析：
    通过prefix+returnVal+后缀 这样的方式得到的实际的物理视图，然后做转发操作
    /WEB-INF/views/+success+.jsp
     */
    @RequestMapping("/helloWorld")
    public String hello(){
        System.out.println("Hello World");
        return "success";
    }
}
