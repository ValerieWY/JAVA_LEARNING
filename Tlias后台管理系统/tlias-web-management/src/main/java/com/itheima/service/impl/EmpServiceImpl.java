package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.*;
import com.itheima.service.EmpLogService;
import com.itheima.service.EmpService;
import com.itheima.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;


/*    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        // 查询总记录数
        Long total = empMapper.count() ;

        // 查询结果列表
//        List<Emp> rows = empMapper.list(page, pageSize); // 第一个参数是起始索引，而不是页码
        // 将页码转换为起始索引，才能传递给mapper.list()
        Integer start = (page - 1) * pageSize;
        List<Emp> rows = empMapper.list(start, pageSize);

        // 返回结果PageResult
        return new PageResult<Emp>(total, rows);
    }*/

//    pageHelper
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        // 1、设置起始索引和每页记录数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

        // 2、执行查询
        List<Emp> empList = empMapper.list(empQueryParam);

        // 3、封装PageResult返回给用户，但该方法需要两个参数，start和rows
        Page<Emp> p = (Page<Emp>) empList;    // PageHelper分页查询结果对象Page
        // 为什么可以强转：Page<E> extends ArrayList<E>，ArrayList<E> implements List<E>

        return new PageResult<Emp>(p.getTotal(), p.getResult());
        // long List<E> ，刚好符合PageResult需要的类型 Long 和 List<T>
    }

    @Transactional  // 事务管理，默认抛出runtimeException时，才会回滚
    @Override
    public void save(Emp emp) {
        // ctrl+alt+t 调出包围方式，选择 try finally 代码块
        try {
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);

//        int i = 1 / 0;

            // 工作经历
            Integer empId = emp.getId() ;
            List<EmpExpr> exprList = emp.getExprList();
            // 工作经历可能为空
//        if(exprList != null) {
            if(!CollectionUtils.isEmpty(exprList)) {
                exprList.forEach(expr -> expr.setEmpId(empId));
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            // 即使在finally块中，如不单独给insertLog方法新建事务还是会被回滚
            // 记录操作日志
            EmpLog empLog = new EmpLog(null,LocalDateTime.now(),"新增员工:" + emp);
            // 因为用户并没有请求这个方法，需要service自己去调用一下自己的方法，输出日志
            empLogService.insertLog(empLog);
        }
    }

    @Override
    // 两个操作要么同时成功要么同时失败，所以要加事务控制
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Integer> ids) {
        // 1、删除基本信息
        empMapper.deleteByIds(ids);

        // 2、删除工作经历
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Emp emp) {
        // 1、更新基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        // 2、更新工作经历（用户可能添加可能删除，都要写）
        // 获取员工ID：不能直接写emp.getId()，因为前面定义批量删除方法时，这里的参数是个List<Integer>，要用Arrays.asList()转换成List<Integer>
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId())); // 删除工作经历
        List<EmpExpr> exprList = emp.getExprList();     // 添加工作经历
//        int i = 1/0;
        if(!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(expr -> expr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }

    }

    @Override
    public List<Emp> listAll() {
        return empMapper.listAll();
    }

    @Override
    public LoginInfo login(Emp emp) {
        // 1、根据用户名和密码查询员工信息
        Emp e = empMapper.selectByUsernameAndPassword(emp);

        // 2、判断该员工是否存在，存在就组装登陆成功信息
        if(e != null) {
            log.info("员工登陆成功：{}", e);

            // 生成JWT令牌
            Map<String,Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());
            String jwt = JwtUtils.generateToken(claims);

            return new LoginInfo(e.getId(), e.getUsername(), e.getName(), jwt);
        }

        // 3、不存在返回null
        return null;

    }


}