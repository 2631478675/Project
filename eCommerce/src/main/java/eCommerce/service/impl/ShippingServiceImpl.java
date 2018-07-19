package eCommerce.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import eCommerce.common.Response;
import eCommerce.dao.ShippingMapper;
import eCommerce.pojo.Shipping;
import eCommerce.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Integer id:都是用户id
 */
@Service("IShippingService")
public class ShippingServiceImpl  implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;
    @Override
    public Response add(Integer id, Shipping shipping) {
        return null;
    }

    @Override
    public Response delete(Integer id, Integer shippingId) {
        return null;
    }

    @Override
    public Response update(Integer id, Shipping shipping) {
        return null;
    }

    @Override
    public Response<Shipping> select(Integer id, Integer shippingId) {
        return null;
    }

    @Override
    public Response<PageInfo> list(Integer id, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(id);
        PageInfo pageInfo = new PageInfo(shippingList);
        return Response.createBySuccess(pageInfo);
    }
}
