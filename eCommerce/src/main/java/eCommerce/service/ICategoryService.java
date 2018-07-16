package eCommerce.service;

import eCommerce.common.Response;
import eCommerce.pojo.Category;

import java.util.List;

public interface ICategoryService {

    Response<Category> addCategory(Category category);


    Response<String> updateCategory(Category category); //可以更改status,name,parentId

    Response<List<Category>> selectChildrenCategoryByCategoryName(Category category);  //根据名字查找（使用递归）

    Response<List<Category>> selectChildrenAndChildrenCategoryByCategoryName(Category category);
}
