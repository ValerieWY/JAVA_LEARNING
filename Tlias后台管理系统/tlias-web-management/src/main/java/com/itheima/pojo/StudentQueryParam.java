package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentQueryParam {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String name;
    private Integer degree;
    private Integer classId;
}
