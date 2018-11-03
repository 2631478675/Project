package product.service;


import common.DecreaseStockInput;
import common.ProductInfoOutput;
import product.VO.Response;
import product.entity.ProductInfo;

import java.util.List;


public interface ProductService {

    /**
     * 查询所有在架商品列表
     */
    Response<List<ProductInfo>> findUpAll();

    /**
     * 查询商品列表
     * @param productIdList
     * @return
     */
    Response<List<ProductInfoOutput>> findList(List<String> productIdList);

    /**
     * 扣库存
     * @param decreaseStockInputList
     */
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}
