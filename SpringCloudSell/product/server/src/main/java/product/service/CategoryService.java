package product.service;

import product.VO.Response;
import product.entity.ProductCategory;

import java.util.List;

/**
 * 类目
 * Created by 廖师兄
 * 2017-12-09 22:06
 */
public interface CategoryService {

    Response<List<ProductCategory>> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
