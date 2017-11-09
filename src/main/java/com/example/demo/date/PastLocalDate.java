package com.example.demo.date;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalDate;

/**
 * Created by ${wujiangjie} on 2017/10/18.
 * 自定义注解：用于校验日期，确保某个日期是过去的值
 */
@Target({ElementType.FIELD})    //用于描述注解的使用范围（即：被描述的注解可以用在什么地方）  FIELD:用于描述域
@Retention(RetentionPolicy.RUNTIME)  // RUNTIME这种类型的Annotations将被JVM保留,所以他们能在运行时被JVM或其他使用反射机制的代码所读取和使用.
@Constraint(validatedBy = PastLocalDate.PastValidator.class)   //验证
@Documented    //表明这个注解应该被 javadoc工具记录. 默认情况下,javadoc是不包括注解的.
public @interface PastLocalDate {
    String message() default "{javax.validation.constraints.Past.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    class PastValidator implements ConstraintValidator<PastLocalDate,LocalDate>{
        public void initialize(PastLocalDate past){}
        public boolean isValid(LocalDate localDate, ConstraintValidatorContext context){
            return localDate == null || localDate.isBefore(LocalDate.now());
        }
    }
}
