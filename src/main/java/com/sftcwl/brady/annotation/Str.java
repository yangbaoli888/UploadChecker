package com.sftcwl.brady.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : String
 * @description :
 * @date : 2024/1/13 2:49 下午
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Validator
public @interface Str {

    int length();
}
