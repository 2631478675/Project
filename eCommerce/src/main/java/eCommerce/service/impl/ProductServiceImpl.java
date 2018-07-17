package eCommerce.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import eCommerce.common.Response;
import eCommerce.dao.CategoryMapper;
import eCommerce.dao.ProductMapper;
import eCommerce.pojo.Category;
import eCommerce.pojo.ProductWithBLOBs;
import eCommerce.service.IProductService;
import eCommerce.util.DateUtil;
import eCommerce.util.PropertiesUtil;
import eCommerce.vo.ProductDetailVo;
import eCommerce.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("IProductService")
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public Response<ProductWithBLOBs> addOrUpdateProduct(ProductWithBLOBs product){
        if(product != null){
                //产品的图片使用子图中第一个作为主图
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray = product.getSubImages().split(",");
                if(subImageArray.length>0){ //如果有图的话，就将第一个作为主图
                    product.setMainImage(subImageArray[0]);
                }
            }

            //判断是增添商品还是更新商品
            if(product.getId() != null){
                int result = productMapper.updateByPrimaryKeyWithBLOBs(product);
                if(result >0 ){
                    return Response.createBySuccess("成功更新商品",product);
                }else {
                    return Response.createByErrorMessage("更新商品失败");
                }
            }else {
                //默认商品开始是可以使用的
                product.setStatus(1);
                int result = productMapper.insert(product);
                if(result >0 ){
                    return Response.createBySuccess("成功新增商品",product);
                }else {
                    return Response.createByErrorMessage("新增商品失败");
                }
            }
        }
        return Response.createByErrorMessage("更新或者添加商品失败，无参数传入");

    }

    @Override
    public Response<String> updateStatusByProductName(ProductWithBLOBs product) {
        if(product.getName()==null || product.getStatus()==null){
            return Response.createByErrorMessage("参数出现空值");
        }
        int result = productMapper.updateStatusByProductName(product);
        if(result>0){
            return Response.createBySuccessMessage("成功修改产品状态");
        }
        return Response.createByErrorMessage("修改产品状态失败");
    }

    @Override
    public Response<ProductDetailVo> getDetailByName(ProductWithBLOBs product) {
        if(product.getName() == null){
            return Response.createByErrorMessage("参数为空");
        }
        ProductWithBLOBs productWithBLOBs = productMapper.selectByName(product.getName());
        //将拿到的商品assemble成前端需要的product形式
        if(productWithBLOBs ==null ){
            return Response.createByErrorMessage("商品下架或者不存在");
        }
        if(productWithBLOBs.getStatus() == 0){
            return Response.createByErrorMessage("商品不出售");
        }
        ProductDetailVo productDetailVo =assembleToProductDetailVo(product);
        return Response.createBySuccess(productDetailVo);
    }

    public Response<PageInfo> getProductList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ProductWithBLOBs> productList = productMapper.selectList();

        List<ProductListVo> productListVoList= new ArrayList<ProductListVo>();

        //将查询到的product放入vo中
        for(ProductWithBLOBs productWithBLOB : productList){
            //将其assemble成vo
            ProductListVo productListVo = assembleProductListVo(productWithBLOB);
            productListVoList.add(productListVo);
        }

        PageInfo pageInfo = new PageInfo(productListVoList); //根据在数据库中查询的数据进行分页

        pageInfo.setList(productListVoList);

        return Response.createBySuccess(pageInfo);

    }

    private ProductDetailVo assembleToProductDetailVo(ProductWithBLOBs product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());

        if(category == null){
            productDetailVo.setParentCategoryId(0);//默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    private ProductListVo assembleProductListVo(ProductWithBLOBs product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }


}
