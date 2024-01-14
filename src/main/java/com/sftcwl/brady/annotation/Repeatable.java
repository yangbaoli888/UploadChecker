package com.sftcwl.brady.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : Repeatable
 * @description :
 * @date : 2024/1/13 2:53 下午
 **/
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Validator
public @interface Repeatable {

    boolean value() default true;

    /**
     * 多个字段联合内容不可重复
     * @return
     */
    String[] fieldNames() default {};
}
