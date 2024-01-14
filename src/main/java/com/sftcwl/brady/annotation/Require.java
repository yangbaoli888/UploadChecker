package com.sftcwl.brady.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : Require
 * @description :
 * @date : 2024/1/13 2:41 下午
 **/
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Validator
public @interface Require {

    /**
     * 字段是否必须填写
     * @return
     */
    boolean blank() default false;
}
