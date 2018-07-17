package eCommerce.controller.backend;


import eCommerce.common.Const;
import eCommerce.common.Response;
import eCommerce.pojo.Category;
import eCommerce.pojo.User;
import eCommerce.service.ICategoryService;
import eCommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/manage/category/")
public class CategoryManageController {


    /**
     * 因为这些功能都是管理员可以做的，所以每个功能都要进行相关的权限判断
     */

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;


    @PostMapping("addCategory")
    @ResponseBody
    public Response<Category> addCategory(HttpSession session, @RequestBody Category category){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return Response.createByErrorMessage("未登录");
        }
        if(user.getRole() == 1){
            categoryService.addCategory(category);
            return Response.createBySuccess("成功操作，增加品类",category);

        }
        return Response.createByErrorMessage("无权限操作");
    }

    @PostMapping("updateCategory")
    @ResponseBody
    public Response<String> updateCategory(HttpSession session,@RequestBody Category category){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return Response.createByErrorMessage("未登录");
        }
        if(user.getRole() == 1){
            categoryService.updateCategory(category);

            return Response.createBySuccess("成功操作，更新品类");

        }
        return Response.createByErrorMessage("无权限操作");
    }


    @PostMapping("selectChildrenCategory")
    @ResponseBody
    //category只需传name即可
    public Response<List<Category>> selectChildrenCategory(HttpSession session, @RequestBody Category category){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return Response.createByErrorMessage("未登录");
        }
        if(user.getRole() == 1){
            return categoryService.selectChildrenCategoryByCategoryName(category);

        }
        return Response.createByErrorMessage("无权限操作");
    }

    @PostMapping("selectChildrenAndChildrenCategory")
    @ResponseBody
    //category只需传name即可
    public Response<List<Category>> selectChildrenAndChildrenCategory(HttpSession session, @RequestBody Category category){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return Response.createByErrorMessage("未登录");
        }
        if(user.getRole() == 1){
            return categoryService.selectChildrenAndChildrenCategoryByCategoryName(category);

        }
        return Response.createByErrorMessage("无权限操作");
    }

}
