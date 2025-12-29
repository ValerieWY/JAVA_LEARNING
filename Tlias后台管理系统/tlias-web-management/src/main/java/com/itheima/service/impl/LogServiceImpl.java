package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.LogMapper;
import com.itheima.pojo.OperateLog;
import com.itheima.pojo.PageResult;
import com.itheima.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public PageResult<OperateLog> page(Integer page, Integer pageSize) {
        // 1、设置起始索引和每页记录数
        PageHelper.startPage(page, pageSize);

        // 2、执行查询
        List<OperateLog> logList = logMapper.list();

        // 3、封装PageResult返回给用户，但该方法需要两个参数，start和rows
        Page<OperateLog> p = (Page<OperateLog>) logList;    // PageHelper分页查询结果对象Page
        // 为什么可以强转：Page<E> extends ArrayList<E>，ArrayList<E> implements List<E>

        return new PageResult<OperateLog>(p.getTotal(), p.getResult());
        // long List<E> ，刚好符合PageResult需要的类型 Long 和 List<T>
    }
}
