package com.moye.mapper;

import com.github.pagehelper.Page;
import com.moye.annotation.AutoFill;
import com.moye.dto.DishPageQueryDTO;
import com.moye.entity.Dish;
import com.moye.enumeration.OperationType;
import com.moye.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    void deleteById(Long id);

    void deleteByIds(List<Long> ids);

    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);
}
