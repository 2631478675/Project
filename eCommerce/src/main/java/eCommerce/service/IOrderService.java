package eCommerce.service;


import eCommerce.common.Response;

public interface IOrderService {
    Response pay(Long orderNo,String path,Integer id);
}
