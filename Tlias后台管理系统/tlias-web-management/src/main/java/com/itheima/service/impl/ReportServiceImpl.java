package com.itheima.service.impl;

import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.ClassOption;
import com.itheima.pojo.JobOption;
import com.itheima.service.ReportService;
import com.itheima.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption getEmpJobData() {
        // 1、调用Mapper接口，获取统计数据
        List<Map<String, Object>> list = empMapper.countEmpJobData(); // list中每一个map都是pos:num

        // 2、组装结果，并返回
        // private List jobList;   // 职位列表
        // private List dataList;  // 职位对应的数据列表

        List<Object> jobList = list.stream().map(map -> map.get("pos")).toList();
        List<Object> dataList = list.stream().map(map -> map.get("num")).toList();

        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }


    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.countStudentDegreeData();
    }

    @Override
    public ClassOption getStudentCountData() {
        List<Map<String, Object>> list = studentMapper.countStudentCountData();
        List<Object> clazzList = list.stream().map(map -> map.get("pos")).toList();
        List<Object> dataList = list.stream().map(map -> map.get("value")).toList();
        return new ClassOption(clazzList, dataList);
    }
}
