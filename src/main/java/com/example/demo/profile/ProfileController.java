package com.example.demo.profile;

import com.example.demo.date.USLocalDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by ${wujiangjie} on 2017/10/17.
 */
@Controller
public class ProfileController {
    private UserProfileSession userProfileSession;
    @Autowired
    public ProfileController(UserProfileSession userProfileSession){
        this.userProfileSession = userProfileSession;
    }
    @ModelAttribute
    public ProfileForm getProfileForm(){
        return userProfileSession.toForm();
    }

    @RequestMapping("/profile")
    public String displayProfile(ProfileForm profileForm){
        return "profile/profilePage";
    }
    //POST方法的映射，当表单被提交的时候，它就会被调用。
    @RequestMapping(value = "/profile",method = RequestMethod.POST,params = {"save"})
    public String saveProfile(@Valid ProfileForm profileForm,BindingResult bindingResult) {   //校验表单ProfileForm
        if( bindingResult.hasErrors()){
            return "profile/profilePage";
        }
        userProfileSession.saveForm(profileForm);
        System.out.println("save OK " + profileForm);
        //return "redirect:/profile";  //跳转到get方法 profile
        return "redirect:/search/mixed;keywords=" + String.join(",",profileForm.getTastes());  //重定向到搜索结果页面
    }

    @ModelAttribute("dateFormat")   //该注解允许我们暴露一个属性给Web页面。在页面中使用时，在日期输入域添加一个占位符即可。
    public String localeFormate(Locale locale){
        return USLocalDateFormatter.getPattern(locale);
    }

    @RequestMapping(value = "/profile",params = {"addTaste"})
    public String addRow(ProfileForm profileForm){
        profileForm.getTastes().add(null);
        return "profile/profilePage";
    }

    @RequestMapping(value = "/profile",params = {"removeTaste"})
    public String removeRow(ProfileForm profileForm, HttpServletRequest request){
        Integer rowId = Integer.valueOf(request.getParameter("removeTaste"));  //得到要移除行的索引
        profileForm.getTastes().remove(rowId.intValue());
        return "profile/profilePage";
    }

}
