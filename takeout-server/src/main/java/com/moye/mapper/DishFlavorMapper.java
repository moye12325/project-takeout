package com.moye.mapper;

import com.moye.annotation.AutoFill;
import com.moye.entity.Dish;
import com.moye.entity.DishFlavor;
import com.moye.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味数据
     *
     * @param flavors
     * @return
     */
    void batchInsert(List<DishFlavor> flavors);

    void deleteByDishId(Long id);
}
