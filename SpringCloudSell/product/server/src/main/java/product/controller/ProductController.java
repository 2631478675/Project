package product.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import product.VO.ProductInfoVO;
import product.VO.ProductVO;
import product.VO.Response;
import product.entity.ProductCategory;
import product.entity.ProductInfo;
import product.service.CategoryService;
import product.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 1. 查询所有在架的商品
     * 2. 获取类目type列表
     * 3. 查询类目
     * 4. 构造数据
     */
    @GetMapping("/list")
    public Response<List<ProductVO>> list() {

        //1. 查询所有在架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2. 获取类目type列表,这里使用的是lambda表达式
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());

        //3. 从数据库查询类目
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //4. 构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : categoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return Response.createBySuccess(productVOList);
    }

//    /**
//     * 获取商品列表(给订单服务用的)
//     * @param productIdList
//     * @return
//     */
//    @PostMapping("/listForOrder")
//    public List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList) {
//        return productService.findList(productIdList);
//    }
//
//    @PostMapping("/decreaseStock")
//    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList) {
//        productService.decreaseStock(decreaseStockInputList);
//    }
}
