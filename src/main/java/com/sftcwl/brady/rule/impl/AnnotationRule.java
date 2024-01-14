package com.sftcwl.brady.rule.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.sftcwl.brady.annotation.*;
import com.sftcwl.brady.annotation.Date;
import com.sftcwl.brady.annotation.Number;
import com.sftcwl.brady.rule.CheckResult;
import com.sftcwl.brady.rule.FieldRule;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : AnnotationRule
 * @description :
 * @date : 2024/1/13 4:12 下午
 **/
public class AnnotationRule extends FieldRule {

    private final Field field;
    private final List<Annotation> validators;

    public AnnotationRule(Field field, List<Annotation> validators) {
        super(field.getName());
        this.field = field;
        this.validators = validators;
    }

    @Override
    public CheckResult check(Object val) {
        if (0 >= this.validators.size()) {
            return CheckResult.pass();
        }

        if (Objects.isNull(val)) {
            Optional<Annotation> require = validators.stream()
                    .filter(validator -> validator.annotationType().equals(Require.class)).findAny();
            if (require.isPresent()) {
                return CheckResult.failed(this.getFieldName() + "必须填写");
            }

        } else {
            for (Annotation validator : validators) {
                Class<? extends Annotation> annotationType = validator.annotationType();
                if (annotationType.equals(Require.class)) {
                    Require require = (Require) validator;
                    String value = ((String) val).trim();
                    boolean blank = require.blank();
                    if (!blank && 0 == value.length()) {
                        return CheckResult.failed(this.getFieldName() + "内容不允许全部为空格");
                    }
                } else if (annotationType.equals(Date.class)) {
                    String value = ((String) val).trim();
                    Date date = (Date) validator;
                    String format = date.format();
                    try {
                        DateTime dateTime = DateUtil.parse(value, format);
                    } catch (Exception e) {
                        //e.printStackTrace();
                        return CheckResult.failed(this.getFieldName() + "格式不正确");
                    }
                } else if (annotationType.equals(Number.class)) {
                    String value = ((String) val).trim();
                    Number number = (Number) validator;
                    if (!NumberUtil.isNumber(value)) {
                        return CheckResult.failed(this.getFieldName() + "不是数值类型");
                    }
                    BigDecimal numberVal = new BigDecimal(value);
                    String min = number.min();
                    if (0 < min.length()) {
                        BigDecimal leftSide = new BigDecimal(min);
                        if (numberVal.compareTo(leftSide) < 0) {
                            return CheckResult.failed(this.getFieldName() + "最小值为 " + min);
                        }
                    }

                    String max = number.max();
                    if (0 < max.length()) {
                        BigDecimal rightSize = new BigDecimal(max);
                        if (numberVal.compareTo(rightSize) > 0) {
                            return CheckResult.failed(this.getFieldName() + "最大值为 " + max);
                        }
                    }
                } else if (annotationType.equals(Str.class)) {
                    String value = ((String) val).trim();
                    Str str = (Str) validator;
                    if (value.length() > str.length()) {
                        return CheckResult.failed(this.getFieldName() + "内容超过字符数上限: " + str.length());
                    }
                }
            }
        }

        return CheckResult.pass();
    }
}
