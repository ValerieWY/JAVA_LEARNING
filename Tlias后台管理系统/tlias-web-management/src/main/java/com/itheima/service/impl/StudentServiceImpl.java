package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import com.itheima.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Student> list(StudentQueryParam studentQueryParam) {
        // 1、设置起始索引和每页记录数
        PageHelper.startPage(studentQueryParam.getPage(), studentQueryParam.getPageSize());

        // 2、执行查询
        List<Student> studentList = studentMapper.list(studentQueryParam);

        // 3、封装PageResult返回给用户，但该方法需要两个参数，start和rows
        Page<Student> p = (Page<Student>) studentList;    // PageHelper分页查询结果对象Page
        // 为什么可以强转：Page<E> extends ArrayList<E>，ArrayList<E> implements List<E>

        return new PageResult<Student>(p.getTotal(), p.getResult());
        // long List<E> ，刚好符合PageResult需要的类型 Long 和 List<T>
    }

    @Override
    public void delete(List<Integer> ids) {
        studentMapper.deleteByIds(ids);
    }

    @Override
    public void save(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        student.setViolationCount((short)0);
        student.setViolationScore((short)0);
        studentMapper.insert(student);
    }

    @Override
    public Student getInfo(Integer id) {

        return studentMapper.getById(id);
    }

    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.updateById(student);
    }

    @Override
    public void updateViolation(Integer id, Short score) {
        studentMapper.updateViolation(id, score);
    }
}
