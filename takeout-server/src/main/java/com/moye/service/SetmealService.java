package com.moye.service;

import com.moye.dto.SetmealDTO;
import com.moye.dto.SetmealPageQueryDTO;
import com.moye.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SetmealService {

     void updateWithDish(SetmealDTO setmealDTO);

     PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

     void deleteBatch(List<Long> ids);

     void saveWithDish(SetmealDTO setmealDTO);
}
