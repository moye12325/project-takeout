package com.moye.controller.admin;

import com.moye.dto.SetmealDTO;
import com.moye.dto.SetmealPageQueryDTO;
import com.moye.result.PageResult;
import com.moye.result.Result;
import com.moye.service.SetmealService;
import com.moye.vo.SetmealVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Tag(name = "套餐管理", description = "套餐管理")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    @GetMapping("/{id}")
    @Operation(summary = "查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        log.info("查询套餐：{}", id);
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @Operation(summary = "更新套餐")
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        log.info("更新套餐: {}", setmealDTO);
        setmealService.updateWithDish(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询套餐")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("分页查询套餐: {}", setmealPageQueryDTO);
        return Result.success(setmealService.pageQuery(setmealPageQueryDTO));
    }

    @DeleteMapping
    @Operation(summary = "批量删除套餐", description = "批量删除套餐")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("批量删除套餐：{}", ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    @PostMapping
    @Operation(summary = "新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐: {}", setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }


    /**
     * 起售停售套餐
     * @param status
     * @param id
     * @return
     */
    @PostMapping("status/{status}")
    @Operation(summary = "起售停售套餐")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("启用禁用分类：{},{}", status, id);
        setmealService.startOrStop(status, id);
        return Result.success();
    }



}
