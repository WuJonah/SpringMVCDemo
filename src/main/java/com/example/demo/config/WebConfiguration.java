package com.example.demo.config;

import com.example.demo.date.USLocalDateFormatter;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.UrlPathHelper;

import java.time.LocalDate;

/**
 * Created by ${wujiangjie} on 2017/10/17.
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter{
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer){
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);  //SpringMVC会默认移除URL中分号之后的字符，这里关闭了    这种默认行为
        configurer.setUrlPathHelper(urlPathHelper);
    }
    @Override
    public void addFormatters(FormatterRegistry registry){
        registry.addFormatterForFieldType(LocalDate.class,new USLocalDateFormatter());
    }
    @Bean
    public LocaleResolver localeResolver(){
        return new SessionLocaleResolver();
    }
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){   //拦截器实现 用于国际化配置
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){  //覆盖并重写了addInterceptors(InterceptorRegistory registory)方法，
        // 这是典型的回调函数——利用该函数的参数registry来添加自定义的拦截器。
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return container -> container.addErrorPages(
                new ErrorPage(MultipartException.class,"/uploadError")
        );
    }

}
