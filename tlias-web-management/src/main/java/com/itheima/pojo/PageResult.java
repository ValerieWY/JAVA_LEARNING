package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 分页结果封装类
public class PageResult<T> {
    // 这两个名字都和接口文档保持一致
    private Long total;
    private List<T> rows;
}
