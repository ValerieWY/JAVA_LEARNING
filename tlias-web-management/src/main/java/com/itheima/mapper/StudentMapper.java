package com.itheima.mapper;

import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    List<Student> list(StudentQueryParam studentQueryParam);

    void deleteByIds(List<Integer> ids);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Student student);

    Student getById(Integer id);

    void updateById(Student student);

    void updateViolation(Integer id, Short score);

    @MapKey("name")
    List<Map<String, Object>> countStudentDegreeData();

    @MapKey("pos")
    List<Map<String, Object>> countStudentCountData();
}
