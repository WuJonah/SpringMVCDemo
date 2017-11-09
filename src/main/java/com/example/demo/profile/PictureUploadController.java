package com.example.demo.profile;

import com.example.demo.config.PicturesUploadProperties;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.Locale;


/** to upload pic
 * Created by ${wujiangjie} on 2017/10/20.
 */
@Controller
@SessionAttributes("picturePath")  //会话属性 能够让模型属性在不同的请求间进行重置
public class PictureUploadController {
  //  public static  final Resource PICTURES_DIR = new FileSystemResource("./pictures");
    private final Resource uploadPath;
    private final Resource pictures;
    private final MessageSource messageSource;
    private final UserProfileSession userProfileSession;

    @Autowired   //构造方法
    public PictureUploadController(PicturesUploadProperties uploadProperties, MessageSource messageSource,UserProfileSession userProfileSession ){
        uploadPath = uploadProperties.getUploadPath();
        pictures = uploadProperties.getPictures();
        this.messageSource = messageSource;
        this.userProfileSession = userProfileSession;
    }
 /*   @ModelAttribute("picturePath")   //模型属性
    public Resource picturePath(){   //展示用户所上传的图片
        return pictures;
    }*/
   /* @RequestMapping("/upload")
    public String uploadPage() {
        return "profile/uploadPage";
    }*/

    @RequestMapping(value = "/uploadedPicture")   //配置页面响应图片
    public void getUploadedPicture(HttpServletResponse response) throws  IOException{
      //  ClassPathResource classPathResource = new ClassPathResource("/images/google.gif");
        Resource res = userProfileSession.getPicturePath();
        if(res == null){
            res = pictures;  //默认图片
        }
        response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(res.getFilename()));
        IOUtils.copy(res.getInputStream(),response.getOutputStream());
    }

    @RequestMapping(value = "/profile",params = "upload",method = RequestMethod.POST)   //上传功能
        public String onUpload(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes ) throws IOException {  //RedirectAttributes专门用于重定向之后还能带参数跳转的的工具类
        if(file.isEmpty()|| !isImage(file)){
            redirectAttributes.addFlashAttribute("error","Incorrect file. Please upload a picture."); //带参数重定向
            return  "redirect:/profile";
        }
        Resource res = copyFileToPic(file);
        userProfileSession.setPicturePath(res);
        return "redirect:profile ";
    }

    @RequestMapping("uploadError")
    public ModelAndView onUploadError(Locale locale){
        ModelAndView md = new ModelAndView("profile/uploadPage");
        md.addObject("error",messageSource.getMessage("upload.file.too.big",null,locale));
        md.addObject("profileForm",userProfileSession.toForm());
        return md;
    }


    private Resource copyFileToPic(MultipartFile file) throws IOException{
        String fileExtension = getFileExtension(file.getOriginalFilename());
        File tempFile = File.createTempFile("pic",fileExtension,uploadPath.getFile());
        try(InputStream in = file.getInputStream() ;
            OutputStream out = new FileOutputStream(tempFile)){
            IOUtils.copy(in,out);
        }
        return new FileSystemResource(tempFile);
    }

    private boolean isImage(MultipartFile file){
        return file.getContentType().startsWith("image");  //getContentType()方法会返回文件的多用途Internet邮件扩展类型。它将会是image/png,image/jpg等，所以判断是否是image开头即可。
    }

    private static String getFileExtension(String name){  //返回name的文件格式名如gif,jpg等
        return   name.substring(name.lastIndexOf("."));
    }

    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOException(Locale locale){
        ModelAndView modelAndView = new ModelAndView("profile/uploadPage");
        modelAndView.addObject("error",messageSource.getMessage("upload.io.exception",null,locale));
        modelAndView.addObject("profileForm",userProfileSession.toForm());
        return modelAndView;
    }

}
