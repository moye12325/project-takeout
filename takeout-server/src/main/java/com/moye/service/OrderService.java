package com.moye.service;

import com.moye.dto.OrdersSubmitDTO;
import com.moye.vo.OrderSubmitVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
