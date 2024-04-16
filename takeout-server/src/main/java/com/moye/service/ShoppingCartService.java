package com.moye.service;

import com.moye.dto.ShoppingCartDTO;
import com.moye.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> showShoppingCart();

    void cleanShopppingCart();

    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
