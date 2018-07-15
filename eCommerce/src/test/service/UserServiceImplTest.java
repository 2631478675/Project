package service;

import eCommerce.common.Response;
import eCommerce.pojo.User;
import eCommerce.service.IUserService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImplTest extends TestBase {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);

    @Autowired
    private IUserService userService;

    private User user = null;
    {
//        userService =
        user = new User();
        user.setUsername("刘元林");
        user.setPassword("123456");
    }


    @Test
    public void testlogin(){
        if(user!=null){
            Response<User> userSelect = userService.login(user);
            System.out.println(userSelect);
        }
       else {
            logger.info("user为空");
        }

    }
}
