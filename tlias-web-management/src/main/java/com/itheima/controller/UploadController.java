package com.itheima.controller;


import com.itheima.pojo.Result;
import com.itheima.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {
    // 注意这里做的操作是，用户在浏览器页面上点了什么操作，浏览器就会把请求发送给服务器，
    // 服务器就会接收到这个请求，对其进行处理，并返回结果给用户。

//    本地磁盘存储方案，项目中不推荐
/*
    // 文件上传请求方式POST，表单往哪里提交，路径就写哪个
    @PostMapping("/upload")
    // 这里是已经接收到了upload页面中用户提交的表单了
*//*    用户传递下来的是upload.html中定义的：
        姓名: <input type="text" name="name"><br>
        年龄: <input type="text" name="age"><br>
        头像: <input type="file" name="file"><br>*//*
    // 保证前端传递过来的参数名和服务器方法形参名一致
    public Result upload(String name, Integer age, MultipartFile file) throws IOException {
        log.info("接收到的参数：{},{},{}", name, age, file);

        // 获取上传的文件名
        String originalFilename = file.getOriginalFilename();
        // UUID
        String newFileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        // 保存文件
        file.transferTo(new File("D:/images/" + newFileName));

        // 程序结束后图片才会上传
        // d5946adb-17f2-4b67-be4b-1683999bc5d2.png出现

        return Result.success();
    }*/

    // 注入OSS工具类
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传：{}",file.getOriginalFilename());

        // 将文件交给阿里云OSS存储管理
        // 获取文件的字节数据和原始名称
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传OSS，url：{}",url);

        return Result.success(url);
    }


}
