package product.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import product.entity.ProductInfo;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {


    @Autowired
   ProductInfoRepository productInfoRepository;

    @Test
    public void findByProductStatus() throws Exception {
       List<ProductInfo> productInfoList = productInfoRepository.findByProductStatus(1);
       for(ProductInfo productInfo : productInfoList){
           System.out.println(productInfo.toString());
       }
    }

    @Test
    public void findByProductIdIn() throws Exception {
    }

}