package com.itheima.mapper;

import com.itheima.pojo.OperateLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogMapper {
    @Select("select e.name as operateEmpName, o.* from operate_log o left join emp e on o.operate_emp_id = e.id order by o.operate_time desc")
    List<OperateLog> list();
}
