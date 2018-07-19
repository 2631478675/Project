package eCommerce.service;


import eCommerce.common.Response;

import java.util.Map;

public interface IOrderService {
    Response pay(Long orderNo,String path,Integer id);

    Response callBack(Map<String, String> stringStringMap);
}
