package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

//    请求路径：/depts
//    请求方式：GET
//    接口描述：该接口用于部门列表数据查询

    // 没有指定请求方式就用POST等什么方式都可以请求
//    @RequestMapping(value = "/depts",method = RequestMethod.GET)
    @GetMapping
    public Result list() {
        System.out.println("查询所有部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    // 删除部门

    // 1、通过原始的HttpServletRequest对象获取请求参数。
/*    @DeleteMapping("/depts")
    public Result delete(HttpServletRequest request) {
        String idStr = request.getParameter("id"); // 获取用户输入的部门编号参数
        int id = Integer.parseInt(idStr);
        System.out.println("删除部门:" + id);
        return Result.success();
    }*/

    @Log
    // 2、通过@RequestParam注解获取请求参数。
    @DeleteMapping
//    @RequestParam("id") Integer deptId)
    // 注意事项：一旦声明了@RequestParam，该参数在请求时必须传递，如果不传递将会报错。（默认required为true）
    public Result delete(@RequestParam Integer id) {   // 参数一样可省略
        System.out.println("删除部门:" + id);
        deptService.deleteById(id);
        return Result.success();
    }

    @Log
    // 新增部门
    @PostMapping
    public Result insert(@RequestBody Dept dept) {
        System.out.println("新增部门：" + dept);
        deptService.insert(dept);
        return Result.success();
    }

    // 根据ID查询部门数据
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        System.out.println("根据ID查询部门数据：" + id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    @Log
    // 修改部门信息
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        System.out.println("修改部门信息：" + dept);
        deptService.update(dept);
        return Result.success();
    }
}
