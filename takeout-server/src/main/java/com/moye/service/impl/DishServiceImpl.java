package com.moye.service.impl;

import com.moye.dto.DishDTO;
import com.moye.entity.Dish;
import com.moye.entity.DishFlavor;
import com.moye.mapper.DishFlavorMapper;
import com.moye.mapper.DishMapper;
import com.moye.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 新增菜品以及口味
     * @param dishDTO
     */
    @Transactional//多个数据表，保持数据的一致性
    @Override
    public void saveWithFlavor(DishDTO dishDTO) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //向菜品表插入一条数据
        dishMapper.insert(dish);

        //获取insert操作后的主键
        Long id = dish.getId();

        //向口味表插入多条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {

            for (DishFlavor flavor : flavors) {
                flavor.setDishId(id);
            }

            //批量插入
            dishFlavorMapper.batchInsert(flavors);
        }

    }
}
