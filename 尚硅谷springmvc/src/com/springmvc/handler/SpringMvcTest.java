package com.springmvc.handler;

import com.springmvc.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


//将user放到Session中
//types 可以使用
@SessionAttributes(value = {"user"},types = {String.class})            //只能放到整个控制器的最前面,对应/**springMvc处理模型数据**/中的方式三



/*
@RequestMapping不只能修饰类，还可以修饰方法
主要作用：为控制器指定处理哪些URL请求
 */
@RequestMapping("/springMvc")
@Controller
public class SpringMvcTest {
    public static final String SUCCESS = "success";

    /**
     * 见新建立的views包
     * 自定义视图,这又出现了问题！！！
     * @return
     */
    //
    @RequestMapping("/testView")
    public String testView(){
        System.out.println("testView");
        return "helloView";
    }


    /**
     *
     * 两大视图之JstlView
     * 项目中使用了Jstl，SpringMvc会自动把视图由InternalResourceView转化为JstlView
     * 前提：如果使用Jstl的fmt标签，则需要在SpringMvc的配置文件中配置国际化资源文件
     */
    /*
    1.导入Jstl标签包：jstl,standard
    2.创建.properties的文件
    3.在success中导入jstl标签
    4.配置国际化资源文件
    为毛会出现乱码？？？将中文的用户名，密码改成那种为啥就行了，但是默认应为成了中文？？/
     */
    /**
     * 标签mvc:view-controller
     * 直接响应转发的页面，无需经过Handler
     * 但是！！！会造成之前的映射方式（经过Handler）不好用
     * 解决方法：配置mvc:annotation-driven标签
     */




    /**
     *
     * 视图和视图解析器
     * InternalResourceViewResolver，InternalResourceView
     * @return
     */
    //视图的工作流程：
    /*
    1.调用目标方法，返回一个  String类型，或者是view类型或者是其他类型
    SpringMvc都会将它转化为ModelAndView类型，通过视图解析器（InternalResourceViewResolver，是支持InternalResourceView的解析器）得到真正的物理视图
    （一个view(InternalResourceView,是搞jsp的（主要是搞在同一个web应用下，通过转发的那个结果）)对象），最终调用viewRander得到结果
     */
    @RequestMapping("/testViewAndViewResolver")
    public String testViewAndViewResolver(){
        System.out.println("testViewAndViewResolver");
        return SUCCESS;
    }





    /**
     * @ModelAttribute：重要且难理解，视频20,21,22
     * 使用场景：见视频17
     * @param id
     * @param map
     */
    /*
     1. 有 @ModelAttribute 标记的方法, 会在每个目标方法执行之前被 SpringMVC 调用!（执行几个目标方法，就会被调用几次）
	 * 2.(不是很重要) @ModelAttribute 注解也可以来修饰目标方法 POJO 类型的入参, 其 value 属性值有如下的作用:
	 * 1). SpringMVC 会使用 value 属性值在 implicitModel 中查找对应的对象, 若存在则会直接传入到目标方法的入参中.
	 * 2). SpringMVC 会以 value 为 key, POJO 类型的对象为 value, 存入到 request 中.
     */
    /*
    重要注释：@ModelAttribute
    注意：存放map中的键的默认与目标方法入参类型的第一个小写字母写的字符串一致（可以改变）
    如下：map.put("abd",user);
    public String testModelAttribute(@ModelAttribute("abc") User user)
     */
    //如果没有这个方法，则不能使用@SessionAttributes(value = {"user"},types = {String.class})
    @ModelAttribute
    public void getUser(@RequestParam (value = "id",required = false) Integer id,Map<String ,Object> map){
        System.out.println("ModelAttribute method");
        if(id!=null){
            //模拟从数据库获得对象
            User user=new User(1,"Tom","123456","12","2631478675@qq.com");
            System.out.println("从数据库获得一个对象");
            user.to();
            //将user放入到map中去
            map.put("user",user);
        }
    }

    /**
     * 上下两个方法中间发生了什么？？？
     * 1.执行@ModelAttribute注释修饰的方法：从数据库中取出对象，把对象到map中，键为user
     * 2.SpringMvc从Map中取出user对象，并将表单的请求参数赋给User对象的对应属性
     * 3.SpringMvc把上述对象作为目标方法的参数
     * @param user
     * @return
     */
    @RequestMapping("/testModelAttribute")
    public String testModelAttribute(User user){
        System.out.println("修改"+user);
        return  SUCCESS;
    }







    /**
     * springMvc处理模型数据
     * mvc设计模式：发一个请求到目标处理器，目标处理器去调用业务方法，将业务方法的返回值在页面上显示出来
     * @return
     */
    /*
    方式一
    目标方法的返回值可以是ModelAndView类型
    其中包括视图和模型信息
    SpringMvc会把modleandview中的modle中的数据放到request域对象中去
     */
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView() {
        String viewName = SUCCESS;
        ModelAndView modelAndView = new ModelAndView(viewName);
        //添加数据模型到ModelAndView中去
        modelAndView.addObject("time", new Date());
        return modelAndView;
    }
    /*
    方式二(比较常用)
    实质上传入的是BindingAwareModelMap
    目标方法可以添加map类型（还可以添加model类型或者是ModelMap类型）
     */
    @RequestMapping("testMap")
    public String testMap(Map<String ,Object> map){
        System.out.println(map.getClass().getName());
        map.put("names", Arrays.asList("Tom","Jerry"));
        return SUCCESS;
    }

    /**
     * @sessionAttribute的使用，注意：只能放在控制器的最开始
     * @param map
     * @return
     */
    /*
    方式三：多个请求之间公用
   */
  @RequestMapping("/testSessionAttributes")
    //将map放到了request中
    public String testSessionAttributes(Map<String,Object> map){
        User user=new User(1,"Tom","13123","26","2631478675@qq.com");
        //对应value,通过其将其放入session中
        map.put("user",user);
        //对应type,通过其将其放入session中
        map.put("school","dianzikeda");
        return SUCCESS;
    }


    /**
     * 使用servlet原生的api作为目标方法的参数
     */
    /*
    mvc的handler所支持的有：
    HttpServletRequest,HttpServletResponse,HttpSession,
    Writer,Reader,OutputStream
     */
    public String testServletAPI(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("testServletAPI" + request + "," + response);
        return SUCCESS;
    }




    /**
     * POJO：普通的Java类
     * @param user
     * @return
     */
    /*比较重要
    POJO：普通的Java类
    SpringMvc确定目标方法POJO类型的入参过程：
    1.首先确定一个key
        1）.若目标方法的POJO类型没有使用@ModelAttribute做为修饰，则key为POJO类名的第一个字母小写
    2.在implicitModel中查找key对应的对象，如存在，则作为入参传入
        1）.如若在@ModelAttribute标记的方法中在Map中保存过，且key和1确定的key一致，则会获取到。
    3.在implicitModel中不存在key对应的对象，则检测当前的handler是否使用@SessionAttributes注释修饰，
    如若使用了该注释，且@SessionAttributes注释的value属性包含了key，则会从HttpSession中获取key所对应的value值，如若存在则直接传到目标函数中去，不存在则抛异常。
    4.如若Handler中没有标识@SessionAttributes注释，或者@SessionAttributes注释的value值中不包含key,则会通过反射才创建POJO类型的参数
    ，传入为目标方法的参数
    5.SpringMvc会把key和POJO类型的对象保存到implicitModel中，进而保存到request中。
     */
    @RequestMapping("/testPojo")
    public String testPojo(User user) {
        System.out.println("testCookieValue:" + user.toString());
        return SUCCESS;
    }




    /**
     * （不常用）
     * @CookieValue: 映射一个Cookie值
     */


    @RequestMapping("/testCookieValue")
    public String testCookieValue(@CookieValue(value = "JSESSIONID") String jsessioid){
        System.out.println("testCookieValue:"+jsessioid);
        return SUCCESS;
    }
    /*
    请求头：用得很少

    @RequestMapping("/testRequestHeader")
    public String testRequestHeader(){
        System.out.println("testRequestHeader");
        return SUCCESS;
    } */

    /**
     * @RequestMapping请求参数
     * @param username
     * @param age
     * @return
     */
    /*
    重要！！！
       @RequestParam,映射请求参数：
       value：请求参数的参数名
       required = false ：该参数是否必须存在？
       eg:
        如果不带参数：@RequestParam(value="age" ,required = false) Integer age
        defaultValue="0":请求参数的默认值，让这个参数的默认值不再是null，而是0
     */
    @RequestMapping(value = "/testRequestParam")
    public String testRequestParam(@RequestParam(value = "username") String username, @RequestParam(value = "age", required = false) Integer age) {
        return SUCCESS;
    }

    /**
     * REST的使用：get,post,delete,put请求
     * @param id
     * @return
     */
    /*
    如何发送put（修改）和delete请求？
    1.需要配置HiddenHttpMethodFilter
    2.需要发送post（新增）请求,在转化成put,delete请求
    3.需要在发送post增加一个隐藏域： <input type="hidden" name="_method" value="PUT"/>
     */
    //delete请求
    @RequestMapping(value = "/testRest/{id}", method = RequestMethod.DELETE)
    public String testRestDelete(@PathVariable Integer id) {
        System.out.println("testRest get:" + id);
        return SUCCESS;
    }
    //put请求
    @RequestMapping(value = "/testRest/{id}", method = RequestMethod.PUT)
    public String testRestPut(@PathVariable Integer id) {
        System.out.println("testRest get:" + id);
        return SUCCESS;
    }
    //post请求，没有参数
    @RequestMapping(value = "/testRest", method = RequestMethod.POST)
    public String testRest() {
        System.out.println("testRest POST:" );
        return SUCCESS;
    }
    //get（获取）请求
    @RequestMapping(value = "/testRest/{id}", method = RequestMethod.GET)
    public String testRest(@PathVariable Integer id) {
        System.out.println("testRest get:" + id);
        return SUCCESS;
    }

    /**
     * @PathVariable的使用：可以映射URL中的占位符到目标方法中
     * @param id
     * @return
     */
    @RequestMapping("/testPathVariable/{id}")
    public String testPathVariable(@PathVariable("id") Integer id) {
        System.out.println("testPathVariable");
        return SUCCESS;
    }

    /**
     * @RequestMapping的使用
     * @RequestMapping支持ANT类型的通配符
     * @return
     */
    //使用params对应请求参数
    //包含username，且年龄不为10
    @RequestMapping(value = "/testParams", params = {"username", "age!=10"})
    public String testParams() {
        System.out.println("testParams");
        return SUCCESS;
    }

    //使用method属性来指定请求方式
    @RequestMapping(value = "/testMethod", method = RequestMethod.POST)
    public String testMethod() {
        System.out.println("testMethod");
        return SUCCESS;
    }

    @RequestMapping("/test")
    public String test() {
        System.out.println("test");
        return SUCCESS;
    }
}
