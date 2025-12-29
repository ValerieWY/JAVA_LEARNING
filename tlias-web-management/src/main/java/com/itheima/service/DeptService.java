package com.itheima.service;


import com.itheima.pojo.Dept;

import java.util.List;

public interface DeptService {
    // 查询所有部门数据
    List<Dept> findAll();

    void deleteById(Integer id);

    void insert(Dept dept);

    Dept getById(Integer id);

    void update(Dept dept);
}
