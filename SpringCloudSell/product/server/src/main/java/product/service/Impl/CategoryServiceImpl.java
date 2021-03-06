package product.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import product.VO.Response;
import product.entity.ProductCategory;
import product.repository.ProductCategoryRepository;
import product.service.CategoryService;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public Response<List<ProductCategory>> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return Response.createBySuccess(productCategoryRepository.findByCategoryTypeIn(categoryTypeList));
    }
}
