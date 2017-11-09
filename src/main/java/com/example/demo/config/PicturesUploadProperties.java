package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
/**
 * Created by ${wujiangjie} on 2017/10/24.
 */

@ConfigurationProperties(prefix = "upload.pictures")  //自动映射类路径下所发现的属性
public class PicturesUploadProperties {
    private Resource uploadPath;   //配置图片上传目录
    private Resource pictures;     //配置页面响应图片

    public Resource getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath =  new DefaultResourceLoader().getResource(uploadPath);//也可以用 FileSystemResource
    }

    public Resource getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = new DefaultResourceLoader().getResource(pictures);  //也可以用 ClassPathResource
    }
}
