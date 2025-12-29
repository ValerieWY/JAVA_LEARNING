package com.itheima.mapper;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;
import java.util.Map;

// 员工数据操作

@Mapper
public interface EmpMapper {
    // 原始分页查询
    // 1、查询符合条件的总记录数
   /* @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
    public Long count();

    *//* 2、查询指定的结果列表（d.name 原字段没有，要在 Emp 自己加，
     然后指定d.name与类变量deptName同名，这样查询出的部门名称就能赋值deptName并赋值给Emp类了）*//*
    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id " +
            "order by e.update_time desc limit #{start},#{pageSize}")
    public List<Emp> list(Integer start, Integer pageSize); // 起始索引和每页展示的记录数*/

    // PageHelper分页插件
   //  @Select("select e.*, d.name deptName from emp as e left join dept as d on e.dept_id = d.id order by e.update_time desc")
    public List<Emp> list(EmpQueryParam empQueryParam);


    // 获取到生成的主键 —— 主键返回
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Emp emp);

    void deleteByIds(List<Integer> ids);

    Emp getById(Integer id);


    void updateById(Emp emp);

    @MapKey("pos")
    // 统计员工职位人数
    List<Map<String, Object>> countEmpJobData();

    @MapKey("name")
    List<Map<String, Object>> countEmpGenderData();

    List<Emp> listAll();

    Emp selectByUsernameAndPassword(Emp emp);


    // 条件查询
/*    select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id
    where e.name like '%欣%' and e.gender = 1 and e.entry_date between '2010-01-01' and '2020-01-01'
    order by e.update_time desc*/
//    注解比较复杂的时候用XML映射文件

}
