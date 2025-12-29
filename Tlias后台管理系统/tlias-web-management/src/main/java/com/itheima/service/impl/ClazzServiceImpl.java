package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.exception.ClassNotEmptyException;
import com.itheima.mapper.ClazzMapper;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public PageResult<Clazz> list(ClazzQueryParam clazzQueryParam) {
        // 1、设置起始索引和每页记录数
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());

        // 2、执行查询
        List<Clazz> clazzList = clazzMapper.list(clazzQueryParam);

        // 3、封装PageResult返回给用户，但该方法需要两个参数，start和rows
        Page<Clazz> p = (Page<Clazz>) clazzList;    // PageHelper分页查询结果对象Page
        // 为什么可以强转：Page<E> extends ArrayList<E>，ArrayList<E> implements List<E>

        return new PageResult<Clazz>(p.getTotal(), p.getResult());
        // long List<E> ，刚好符合PageResult需要的类型 Long 和 List<T>
    }

    @Override
    public void deleteById(Integer id) {
        // 如果班级下有学生，提示错误信息："对不起, 该班级下有学生, 不能直接删除"。
        if (clazzMapper.getStudentCountByClazzId(id) > 0) {
            throw new ClassNotEmptyException("对不起, 该班级下有学生, 不能直接删除");
        }
        clazzMapper.deleteById(id);
    }

    @Override
    public void save(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazz.setCreateTime(LocalDateTime.now());   // 新增的时候要设置createTime哦
        clazzMapper.insert(clazz);
    }

    @Override
    public Clazz getInfo(Integer id) {
        return clazzMapper.getById(id);
    }

    @Override
    public void update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
//        clazz.setCreateTime(LocalDateTime.now());
        clazzMapper.updateById(clazz);

    }

    @Override
    public List<Clazz> listAll() {
        return clazzMapper.listAllClazz();
    }


}
