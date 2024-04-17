package com.moye.mapper;

import com.moye.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {
    /**
     * 插入订单数据
     * @param order
     */
    void insert(Orders order);

    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String outTradeNo);

    void update(Orders orders);
}
