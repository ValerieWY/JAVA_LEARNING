package com.itheima.controller;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

/*    // 分页查询
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Integer gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {        // 指定默认值
        log.info("分页查询，参数：{}，{},{},{},{},{}", page, pageSize, name, gender, begin, end);
        PageResult<Emp> pageResult = empService.page(page, pageSize, name, gender, begin, end);
        return Result.success(pageResult);
    }*/

    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {        // 指定默认值
        log.info("分页查询，参数：{}，{},{},{},{},{}", empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }


    // 增加员工
    @PostMapping
    public Result save(@RequestBody Emp emp) {
        log.info("请求参数：{}", emp);
        empService.save(emp);
        return Result.success();
    }

    // 删除员工
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除员工，参数：{}", ids);
        empService.delete(ids);
        return Result.success();
    }

    // 查询员工信息
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) { // PathVariable接收路径参数
        log.info("查询员工信息，参数：{}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    // 修改员工信息
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("修改员工信息，参数：{}", emp);
        empService.update(emp);
        return Result.success();
    }

    // 查询所有员工
    @GetMapping("/list")
    public Result list() {
        log.info("查询所有员工");
        List<Emp> list = empService.listAll();
        return Result.success(list);
    }
}
