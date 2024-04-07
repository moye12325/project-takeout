package com.moye.mapper;

import com.github.pagehelper.Page;
import com.moye.dto.CategoryPageQueryDTO;
import com.moye.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 根据主键动态修改sql
     *
     * @param category
     */
    void update(Category category);

    void insert(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    List<Category> list(Integer type);

    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);
}
