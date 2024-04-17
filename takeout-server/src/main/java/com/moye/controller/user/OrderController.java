package com.moye.controller.user;


import com.github.pagehelper.PageHelper;
import com.moye.dto.OrdersDTO;
import com.moye.dto.OrdersPaymentDTO;
import com.moye.dto.OrdersSubmitDTO;
import com.moye.result.PageResult;
import com.moye.result.Result;

import com.moye.service.OrderService;
import com.moye.vo.OrderPaymentVO;
import com.moye.vo.OrderSubmitVO;
import com.moye.vo.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name = "C端-订单相关接口")
@RequestMapping("/user/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    @Operation(summary = "用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单：{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @Operation(summary = "订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        //模拟交易成功
        orderService.paySuccess(ordersPaymentDTO.getOrderNumber());
        return Result.success(orderPaymentVO);
    }

    /**
     * 查询历史订单
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    @GetMapping("/historyOrders")
    @Operation(summary = "历史订单查询")
    public Result<PageResult> page(int page, int pageSize, Integer status) {
        log.info("查询历史订单");
        PageResult pageResult = orderService.pageQuery4User(page,pageSize,status);
        return Result.success(pageResult);
    }
}
