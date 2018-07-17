package eCommerce.service.impl;

import eCommerce.common.Response;
import eCommerce.dao.CategoryMapper;
import eCommerce.pojo.Category;
import eCommerce.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service("ICategoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Response<Category> addCategory(Category category) {
        if(category.getName() == null|| category.getParentId() == null){
            return Response.createByErrorMessage("未能添加新品类，添加信息不完整");
        }
        category.setStatus(true);
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        int result = categoryMapper.insert(category);
        if(result > 0) {
            return Response.createBySuccess("成功添加新品类",category);
        }
        return Response.createByErrorMessage("失败添加新品类");
    }

    /**
     * 可以更改name，status，parentId
     * @param category
     * @return
     */
    @Override
    public Response<String> updateCategory(Category category) {
        //根据categoryId(前端提供)选择性的update
        int result = categoryMapper.updateByPrimaryKeySelective(category);
        if(result > 0){
            return Response.createBySuccessMessage("更新品类信息成功");
        }
        return Response.createByErrorMessage("失败更新品类信息");
    }

    @Override
    public Response<List<Category>> selectChildrenCategoryByCategoryName(Category category) {
        //根据名字查找id
        int categoryId = categoryMapper.selectCategoryIdByName(category.getName());
        List<Category> categories= categoryMapper.selectChildrenCategoryByName(categoryId);
        if(CollectionUtils.isEmpty(categories)){
            return Response.createBySuccessMessage("未找到当前分类的子类");   //有可能category.name不存在？之后完善
        }
        return Response.createBySuccess(categories);
    }

    //使用递归查找子节点和子子节点。。。
    public Response<List<Category>> selectChildrenAndChildrenCategoryByCategoryName(Category category){
        //根据名字查找id
        int categoryId = categoryMapper.selectCategoryIdByName(category.getName());

        Set<Category> categories = new HashSet<>();
        findChildCategory(categories,categoryId);
        List<Category> categoryList = new ArrayList<>(categories);
        return Response.createBySuccess(categoryList);
    }



    //递归算法
    private Set<Category> findChildCategory(Set<Category> categories,int categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categories.add(category);
        }

        //查找子节点,递归算法一定要有一个退出的条件
        List<Category> categoryList = categoryMapper.selectChildrenCategoryByName(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categories,categoryItem.getId());
        }
        return categories;
    }

}
