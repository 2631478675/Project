## 服务网关
> 为啥要使用？网关瘫痪，系统全挂，所以保证72小时正常工作
- 稳定性、高可用
- 性能、并发性
- 安全性
- 扩展性
#### 常用的网关方案
- Nginx+Lua
- Kong
- Tyk
- Spring cloud Zuul
    - 认证、鉴权、限流、动态路由、监控、弹性、安全、负载均衡。。。
#### Zuul
- 特点
    - 路由+过滤器=Zuul
    - 核心是一系列的过滤器
- Zuul的四种过滤器API
    - 前置（Pre）：参数校验等、鉴权、限流（最前面）、请求转发
    - 后置（Post）：处理返回结果
    - 路由（Route）：将外部请求转发到远程服务（如order）上
    - 错误（Error）：统一异常处理
- Zuul的架构图

![image](http://p3379ff71.bkt.clouddn.com/30ac4c3884624389a0e451a42c16465c.jpg)
- Zuul的请求生命周期

![image](http://p3379ff71.bkt.clouddn.com/zuul-core.png)
#### Zuul的路由转发功能
- 主类增加注解@EnableZuulProxy
- 访问product的list
    - http://localhost:8080/product/list
- 使用网关访问
    - http://localhost:9000/product/product/list（增添了应用的名字）
- 如何自定义路由 ，而不是使用应用的名字

```
zuul:
  routes: 
    myProduct: ###随便写
      path: /myProduct/**  #自定义路由名字
      serviceId: product #原应用的名字
```
- 如何查看路由规则
    - 添加配置
    - 访问url： http://localhost:9090/application/routes
- 排除某些url
- cookie本来要传递给后端，因为使用了zuul的组件之后，默认就传递不过来了
    - 可以在配置中解决，使用敏感头为空
- 自己实现动态路由 
- ==nginx和zuul混搭==，nginx对外暴露一个接口，nginx把多个请求转发到zuul服务上，
- 过滤器的使用
    - 前置：[限流](https://github.com/marcosbarbero/spring-cloud-zuul-ratelimit)
    - 后置
- 跨域问题