package com.sftcwl.brady.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : Number
 * @description :
 * @date : 2024/1/13 2:45 下午
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Validator
public @interface Number {

    String min() default "";

    String max() default "";
}
