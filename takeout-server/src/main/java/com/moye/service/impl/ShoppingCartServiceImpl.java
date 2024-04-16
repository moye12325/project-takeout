package com.moye.service.impl;

import com.moye.context.BaseContext;
import com.moye.dto.ShoppingCartDTO;
import com.moye.entity.Dish;
import com.moye.entity.Setmeal;
import com.moye.entity.ShoppingCart;
import com.moye.mapper.DishMapper;
import com.moye.mapper.SetmealMapper;
import com.moye.mapper.ShoppingCartMapper;
import com.moye.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //判断当前加入购物车中的商品是否已经存在
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        //如果已经存在了，只需要将数量+1
        if (list != null && list.size() > 0) {
            //说明获取到了
             shoppingCart = list.get(0);
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);//更新语句，更新number
            shoppingCartMapper.updateNumberById(shoppingCart);
        }else {
            //不存在则需要插入一条购物车数据
            Long dishId = shoppingCartDTO.getDishId();
            if (dishId != null) {
                //添加到购物车的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());

//                shoppingCart.setNumber(1);
//                shoppingCart.setCreateTime(LocalDateTime.now());
            }else {
                Long setmealId = shoppingCartDTO.getSetmealId();
                //添加进来的是套餐
                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());

//                shoppingCart.setNumber(1);
//                shoppingCart.setCreateTime(LocalDateTime.now());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            //统一插入数据
            shoppingCartMapper.insert(shoppingCart);
        }
    }
}
