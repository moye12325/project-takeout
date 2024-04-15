package com.moye.service;

import com.moye.dto.SetmealDTO;
import com.moye.dto.SetmealPageQueryDTO;
import com.moye.entity.Setmeal;
import com.moye.result.PageResult;
import com.moye.vo.DishItemVO;
import com.moye.vo.SetmealVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SetmealService {

     void updateWithDish(SetmealDTO setmealDTO);

     PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

     void deleteBatch(List<Long> ids);

     void saveWithDish(SetmealDTO setmealDTO);

     void startOrStop(Integer status, Long id);

     SetmealVO getById(Long id);

    List<Setmeal> list(Setmeal setmeal);

     List<DishItemVO> getDishItemById(Long id);
}
