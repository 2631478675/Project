package product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import product.entity.ProductCategory;

import java.util.List;


public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    /**
     * 根据种类编号查询
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
