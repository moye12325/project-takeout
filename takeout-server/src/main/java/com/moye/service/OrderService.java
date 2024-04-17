package com.moye.service;

import com.moye.dto.OrdersPaymentDTO;
import com.moye.dto.OrdersSubmitDTO;
import com.moye.result.PageResult;
import com.moye.vo.OrderPaymentVO;
import com.moye.vo.OrderSubmitVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    void paySuccess(String outTradeNo);

    PageResult pageQuery4User(int page, int pageSize, Integer status);
}
