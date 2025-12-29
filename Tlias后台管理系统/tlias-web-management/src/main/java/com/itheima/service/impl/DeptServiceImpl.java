package com.itheima.service.impl;

import com.itheima.exception.DeptNotEmptyException;
import com.itheima.mapper.DeptMapper;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

//    依赖注入
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll() {
        // 调用Mapper接口，返回数据
        return deptMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        // 删除部门之前，先判断部门下是否有员工
        if (deptMapper.getCountByDeptId(id) > 0) {
            throw new DeptNotEmptyException("对不起, 该部门下有员工, 不能直接删除");
        }
        deptMapper.deleteById(id);
    }

    @Override
    public void insert(Dept dept) {
        // 自动补全基础属性 createTime,updateTime
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }

    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }


}
