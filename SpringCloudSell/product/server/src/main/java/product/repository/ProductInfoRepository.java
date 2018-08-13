package product.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import product.entity.ProductInfo;

import java.util.List;


public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);

    List<ProductInfo> findByProductIdIn(List<String> productIdList);
}
