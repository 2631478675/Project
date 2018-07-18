package eCommerce.controller.backend;


import com.github.pagehelper.PageInfo;
import eCommerce.common.Const;
import eCommerce.common.Response;
import eCommerce.pojo.Product;
import eCommerce.pojo.ProductWithBLOBs;
import eCommerce.pojo.User;
import eCommerce.service.IProductService;
import eCommerce.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IProductService productService;

    @RequestMapping("addProduct")
    @ResponseBody
    public Response<ProductWithBLOBs> addProduct(HttpSession session, @RequestBody ProductWithBLOBs product){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return Response.createByErrorMessage("用户未登录");
        }
        if(user.getRole() == 0){
            return Response.createByErrorMessage("无权限操作");
        }
        return productService.addOrUpdateProduct(product);
    }

    //修改产品状态:status为1，是可以销售的产品，status为0则表示此产品不可以再销售
    @RequestMapping("updateStatus")
    @ResponseBody
    public Response<String> updateStatus(HttpSession session, @RequestBody ProductWithBLOBs product){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return Response.createByErrorMessage("用户未登录");
        }
        if(user.getRole() == 0){
            return Response.createByErrorMessage("无权限操作");
        }
        return productService.updateStatusByProductName(product);
    }

    //获取商品详情（detail）
    @RequestMapping("updateStatus")
    @ResponseBody
    public Response<ProductDetailVo> getDetail(HttpSession session, @RequestBody ProductWithBLOBs product){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return Response.createByErrorMessage("用户未登录");
        }
        if(user.getRole() == 0){
            return Response.createByErrorMessage("无权限操作");
        }
       return productService.getDetailByName(product);
    }


    //获取商品列表（需要分页）
    @RequestMapping("list")
    @ResponseBody
    public Response<PageInfo> list(HttpSession session, int pageNum, int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return Response.createByErrorMessage("用户未登录");
        }
        if(user.getRole() == 0){
            return Response.createByErrorMessage("无权限操作");
        }
        return productService.getProductList(pageNum,pageSize);
    }


    //将商品图片上传到服务器
    //获取商品列表（需要分页）
    @RequestMapping("upload")
    @ResponseBody
    public Response upload(HttpSession session, MultipartFile file, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return Response.createByErrorMessage("用户未登录");
        }
        if(user.getRole() == 0){
            return Response.createByErrorMessage("无权限操作");
        }
        //创建上传的路径
        String path = request.getSession().getServletContext().getRealPath("upload");

//        return productService.getProductList(pageNum,pageSize);
        return null;
    }
}
