package com.moye.service;

import com.moye.dto.CategoryDTO;
import com.moye.dto.CategoryPageQueryDTO;
import com.moye.entity.Category;
import com.moye.result.PageResult;

import java.util.List;

public interface CategoryService {
    void startOrStop(Integer status, Long id);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void save(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void deleteById(Long id);

    List<Category> list(Integer type);
}
