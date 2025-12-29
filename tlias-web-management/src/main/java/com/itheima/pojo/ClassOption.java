package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassOption {
    private List clazzList;   // 职位列表
    private List dataList;  // 职位对应的数据列表
}
