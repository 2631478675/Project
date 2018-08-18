package order.controller.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class RestTempleteController {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/getProductMsg")
    public String getProductMsg() {

        /**
         * 第一种方式：不适用高复用、负载均衡
         * 直接使用restTemplate,url写死
         */
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject("http://localhost:8080/msg",String.class);
//        log.info("使用注解式日志");


        /**
         * 第二种方式：利用loadBalancerClient通过应用的名字和端口号获取URL
//         */
//        RestTemplate restTemplate = new RestTemplate();
//        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");  //eureka中application的名字
//        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort());
//        String response = restTemplate.getForObject(url, String.class);

        /**
         * 第三种方式
         */

        String response = restTemplate.getForObject("http://PRODUCT/spring/spring",String.class);

        return response;
    }
}
