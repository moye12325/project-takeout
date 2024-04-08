package com.moye.mapper;

import com.moye.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查询套餐id
     * @param dishIds
     * @return
     */
    List<Long> getSetmealDishIds(List<Long> dishIds);


    void batchInsert(List<SetmealDish> setmealDishes);

    @Delete("delete from setmeal_dish where dish_id = #{id}")
    void deleteByDishId(Long id);

    void deleteByDishIds(List<Long> ids);

    List<SetmealDish> getBySetmealId(Long id);
}
