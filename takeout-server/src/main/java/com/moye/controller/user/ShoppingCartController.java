package com.moye.controller.user;

import com.moye.dto.ShoppingCartDTO;
import com.moye.entity.ShoppingCart;
import com.moye.result.Result;

import com.moye.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Tag(name = "C端-购物车相关接口")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    @GetMapping("/list")
    @Operation(summary = "查看购物车")
    public Result<List<ShoppingCart>> list() {
        log.info("查看购物车");
        List<ShoppingCart> list = shoppingCartService.showShoppingCart();
        return Result.success(list);
    }

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    @Operation(summary = "添加购物车")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {

        log.info("添加购物车：{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 情况购物车
     * @return
     */
    @DeleteMapping("/clean")
    @Operation(summary = "清空购物车")
    public Result<String> clean(){
        shoppingCartService.cleanShopppingCart();
        return Result.success();
    }


}
