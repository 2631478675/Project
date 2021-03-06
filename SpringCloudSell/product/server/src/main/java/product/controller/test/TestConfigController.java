package product.controller.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring/")
public class TestConfigController {

    @Value("${spring.application.name}")
    String spring;

    @GetMapping("spring")
    public String spring(){
        return spring;
    }
}
