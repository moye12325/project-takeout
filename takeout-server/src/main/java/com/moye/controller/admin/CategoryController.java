package com.moye.controller.admin;

import com.moye.dto.CategoryDTO;
import com.moye.dto.CategoryPageQueryDTO;
import com.moye.dto.EmployeeDTO;
import com.moye.entity.Category;
import com.moye.result.PageResult;
import com.moye.result.Result;
import com.moye.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jaxb.core.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Tag(name = "分类管理", description = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @Operation(summary = "修改分类", description = "修改分类")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO) {

        log.info("修改分类信息：{}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success("修改成功");
    }

    /**
     * 分页查询分类
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("page")
    @Operation(summary = "分页查询分类", description = "分页查询分类")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用、禁用分类
     * @param status
     * @param id
     * @return
     */
    @PostMapping("status/{status}")
    @Operation(summary = "启用、禁用分类", description = "启用、禁用分类")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("启用禁用员工账号：{},{}", status, id);
        categoryService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @Operation(summary = "新增分类", description = "新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
//        System.out.println("当前线程的id：" + Thread.currentThread().getId());
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @Operation(summary = "删除分类")
    public Result<String> deleteById(Long id){
        log.info("删除分类：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
