package eCommerce.service;

import com.github.pagehelper.PageInfo;
import eCommerce.common.Response;
import eCommerce.pojo.ProductWithBLOBs;
import eCommerce.vo.ProductDetailVo;



public interface IProductService {
    Response<ProductWithBLOBs> addOrUpdateProduct(ProductWithBLOBs product);


    Response<String> updateStatusByProductName(ProductWithBLOBs product);


    Response<ProductDetailVo> getDetailByName(ProductWithBLOBs product);

    Response<PageInfo> getProductList(int pageNum, int pageSize);
}
