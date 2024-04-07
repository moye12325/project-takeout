package com.moye.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.moye.context.BaseContext;
import com.moye.dto.CategoryDTO;
import com.moye.dto.CategoryPageQueryDTO;
import com.moye.entity.Category;
import com.moye.entity.Employee;
import com.moye.mapper.CategoryMapper;
import com.moye.mapper.DishMapper;
import com.moye.mapper.SetmealMapper;
import com.moye.result.PageResult;
import com.moye.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void startOrStop(Integer status, Long id) {

        // update employee set status = ? where id = ?
        Category category = new Category();
        category.setId(id);
        category.setStatus(status);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {

        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void save(CategoryDTO categoryDTO) {

        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.insert(category);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);

        //设置修改时间、修改人
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);
    }

    @Override
    public void deleteById(Long id) {

        //查询当前分类是否关联菜品，关联了则抛出异常
        Integer count = dishMapper.countByCategoryId(id);
        if (count > 0) {
            throw new RuntimeException("当前分类已关联菜品，无法删除");
        }

        //查询当前菜品是否关联了套餐 关联了则抛出异常
        Integer count1 = setmealMapper.countByCategoryId(id);
        if (count1 > 0) {
            throw new RuntimeException("当前分类已关联套餐，无法删除");
        }

        categoryMapper.deleteById(id);
    }

    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
}
