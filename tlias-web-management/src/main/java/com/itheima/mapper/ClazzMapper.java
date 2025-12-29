package com.itheima.mapper;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface ClazzMapper {
    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    void deleteById(Integer id);

    // 获取到生成的主键 —— 主键返回
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Clazz clazz);

    Clazz getById(Integer id);

    void updateById(Clazz clazz);

    List<Clazz> listAllClazz();

    int getStudentCountByClazzId(Integer id);
}
