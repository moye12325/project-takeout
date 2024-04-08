package com.moye.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.moye.constant.MessageConstant;
import com.moye.constant.StatusConstant;
import com.moye.dto.SetmealPageQueryDTO;
import com.moye.entity.Dish;
import com.moye.entity.DishFlavor;
import com.moye.entity.Setmeal;
import com.moye.entity.SetmealDish;
import com.moye.exception.DeletionNotAllowedException;
import com.moye.mapper.SetmealDishMapper;
import com.moye.mapper.SetmealMapper;
import com.moye.result.PageResult;
import com.moye.service.SetmealService;
import com.moye.dto.SetmealDTO;
import com.moye.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Transactional
    @Override
    public void updateWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);

        //TODO:套餐关联的菜品

    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {

        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Transactional
    @Override
    public void deleteBatch(List<Long> ids) {

        //套餐是否能够删除-> 是否存在起售中的套餐
        for (Long id : ids) {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }

        //删除套餐表
        //删除套餐菜品关系表中的数据
//        for (Long id : ids) {
//            setmealMapper.deleteById(id);
//            //删除菜品关联的口味表
//            setmealDishMapper.deleteByDishId(id);
//        }
        setmealMapper.deleteByIds(ids);
        setmealDishMapper.deleteByDishIds(ids);
    }

    @Transactional
    @Override
    public void saveWithDish(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //向套餐表插入数据
        setmealMapper.insert(setmeal);
        //获取生成的套餐id
        Long id = setmeal.getId();
        //向菜品表插入数据
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(id);
            }
            setmealDishMapper.batchInsert(setmealDishes);
        }
    }

}
