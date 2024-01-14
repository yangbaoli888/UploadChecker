package com.sftcwl.brady.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : AnnotationUtils
 * @description :
 * @date : 2024/1/13 10:33 下午
 **/
public class AnnotationUtils {

    public static List<Annotation> getAnnotations(Field field, final Class clazz) {
        Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
        List<Annotation> validators = new ArrayList<>();
        for (Annotation validator : declaredAnnotations) {
            Annotation annotation = validator.annotationType().getAnnotation(clazz);
            if (null != annotation) {
                validators.add(validator);
            }
        }
        return validators;
    }
}
