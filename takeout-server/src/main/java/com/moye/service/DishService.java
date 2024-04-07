package com.moye.service;


import com.moye.dto.DishDTO;
import com.moye.dto.DishPageQueryDTO;
import com.moye.result.PageResult;

import java.util.List;

public interface DishService {

    void saveWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);
}
