package com.moye.mapper;

import com.github.pagehelper.Page;
import com.moye.annotation.AutoFill;
import com.moye.dto.SetmealPageQueryDTO;
import com.moye.entity.Setmeal;
import com.moye.entity.SetmealDish;
import com.moye.enumeration.OperationType;
import com.moye.vo.DishItemVO;
import com.moye.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     *
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);


    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);


    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    @Delete("delete from setmeal where id = #{id}")
    void deleteById(Long id);

    void deleteByIds(List<Long> ids);

    List<DishItemVO> getDishItemBySetmealId(Long id);

    List<Setmeal> list(Setmeal setmeal);
}
