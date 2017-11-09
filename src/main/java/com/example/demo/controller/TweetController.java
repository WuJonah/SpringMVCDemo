/*
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.api.TweetsResources;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class TweetController {
    private Twitter twitter = (Twitter) new TwitterFactory().getInstance();

    @RequestMapping("/")
    public String home(){
        return "searchPage";
    }
    @RequestMapping(value = "/postSearch", method = RequestMethod.POST)
    public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String search = request.getParameter("search");
        if(search.toLowerCase().contains("struts")){   //如果用户搜索包含struts这个术语的话，重定向到searchPage页面并且使用flash属性添加一点错误信息
            redirectAttributes.addFlashAttribute("error","Try using spring instead!");
            return "redirect:/";
        }
        redirectAttributes.addAttribute("search",search);
        return "redirect:result";
    }

    @RequestMapping("/result")
    public String hello(@RequestParam(defaultValue =
            "masterSpringMVC4") String search, Model model) throws TwitterException {
   //     SearchResults searchResults = twitter.searchOperations().
   //             search(search);
        String user= "JonahWu";
        List<Status> statuses;
        statuses = twitter.getUserTimeline(user);
        TweetsResources tweetsResources = twitter.tweets();
        System.out.println("Showing@"+ user + "'user timeline.");
        for (Status status : statuses) {
            //status.getRetweetCount() 转推的数目
            // status.getFavoriteCount() 点赞次数
            // status.getSource() 发布的客户端类型
            // status.getCreatedAt() 发布时间
            // status.getGeoLocation() 地点
            // status.getId() 获取该条tweet Id
            String content = status.getText(); // tweet内容
            String ScreenName = status.getUser().getScreenName();
            Date publishDate = status.getCreatedAt();
            System.out.println("@" + ScreenName + "--" + content + "--" + publishDate);
        }
        model.addAttribute("message", statuses.get(0).getText()); //获取第一篇有关推文
        model.addAttribute("search",search);

        return "resultPage3rd";
    }


//    @ResponseBody
*/
/*    public String hello(){

 //       return  "Hello World!";
        return  "resultPage";
    }*//*

*/
/*    public String hello(Model model){ //在视图中展示来自服务端的数据
        model.addAttribute("message","Hello from the controller");
        return  "resultPage2nd";
    }*//*

    //public String hello(@RequestParam(value = "name",required = false,defaultValue = "World") String userName,Model model){   //通过传入查询参数到URL来获取用户的输入
    //    model.addAttribute("message","Hello," + userName);
    //    return "resultPage2nd";
    //}

}
*/
