package eCommerce.service;

import com.github.pagehelper.PageInfo;
import eCommerce.common.Response;
import eCommerce.pojo.Shipping;

public interface IShippingService {
    Response add(Integer id, Shipping shipping);

    Response delete(Integer id, Integer shippingId);

    Response update(Integer id, Shipping shipping);

    Response<Shipping> select(Integer id, Integer shippingId);

    Response<PageInfo> list(Integer id, int pageNum, int pageSize);
}
