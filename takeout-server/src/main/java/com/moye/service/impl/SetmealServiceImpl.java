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
import com.moye.mapper.DishMapper;
import com.moye.mapper.SetmealDishMapper;
import com.moye.mapper.SetmealMapper;
import com.moye.result.PageResult;
import com.moye.service.SetmealService;
import com.moye.dto.SetmealDTO;
import com.moye.vo.DishItemVO;
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
    @Autowired
    private DishMapper dishMapper;

    @Transactional
    @Override
    public void updateWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);

        //删除套餐菜品关系表中的数据
        Long id = setmealDTO.getId();
        //操作setmeal_dish表，del
        setmealDishMapper.deleteByDishId(id);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(id));
        setmealDishMapper.batchInsert(setmealDishes);

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

    @Override
    public void startOrStop(Integer status, Long id) {
        //起售套餐时，判断套餐内是否有停售菜品，有停售菜品提示"套餐内包含未启售菜品，无法启售"

        if (status == StatusConstant.ENABLE) {
            List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);//套餐里面的菜品集合

            for (SetmealDish setmealDish : setmealDishes) {//遍历集合
                Dish dish = dishMapper.getById(setmealDish.getDishId());//拿到对应的菜品
                if (dish.getStatus() == StatusConstant.DISABLE) {//检查菜品状态
                    throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            }
        }

        //更新套餐状态
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        setmealMapper.update(setmeal);
    }

    @Override
    public SetmealVO getById(Long id) {
        //根据id查套餐数据
        Setmeal setmeal = setmealMapper.getById(id);
        if (setmeal != null) {
            SetmealVO setmealVO = new SetmealVO();
            BeanUtils.copyProperties(setmeal, setmealVO);
            //根据套餐id查套餐菜品关系表
            List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
            setmealVO.setSetmealDishes(setmealDishes);
            return setmealVO;
        }
        return null;
    }

    /**
     * 条件查询
     *
     * @param setmeal
     * @return
     */
    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     *
     * @param id
     * @return
     */
    @Override
    public List<DishItemVO> getDishItemById(Long id) {

        return setmealMapper.getDishItemBySetmealId(id);
    }

}
