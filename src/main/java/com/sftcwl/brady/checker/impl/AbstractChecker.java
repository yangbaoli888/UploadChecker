package com.sftcwl.brady.checker.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.sftcwl.brady.annotation.Repeatable;
import com.sftcwl.brady.annotation.Validator;
import com.sftcwl.brady.checker.IChecker;
import com.sftcwl.brady.rule.CheckResult;
import com.sftcwl.brady.rule.FieldRule;
import com.sftcwl.brady.rule.impl.AnnotationRule;
import com.sftcwl.brady.rule.impl.NonRepeatRule;
import com.sftcwl.brady.utils.AnnotationUtils;
import com.sun.istack.internal.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : AbstractChecker
 * @description :
 * @date : 2024/1/13 3:02 下午
 **/
public abstract class AbstractChecker<T> implements IChecker<T> {
    private final List<Field> fields;
    private final Map<Long, String> errMessages;
    private final Map<String, FieldRule> RULE_MAP;
    private final Map<String, List<Annotation>> fieldAnnotations;
    private final AtomicLong rowNumber = new AtomicLong(0);
    private final List<Repeatable> repeatableList = new ArrayList<>();

    public AbstractChecker(@NotNull Map<Long, String> errMsgMap) {
        this.errMessages = errMsgMap;
        this.RULE_MAP = new HashMap<>(16);
        this.fields = new ArrayList<>();

        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            Type type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
            if (type instanceof Class) {
                Class<?> clazz = (Class<?>) type;
                this.fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
                for (Field field : fields) {
                    field.setAccessible(true);
                }
            }
        }

        fieldAnnotations = new HashMap<>(this.fields.size());
    }

    @Override
    public T rowCheck(T row) {
        long rowId = rowNumber.addAndGet(1L);
        for (Field field : fields) {
            String fieldName = field.getName();
            Object val = null;
            try {
                val = field.get(row);
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }

            List<Annotation> validators = fieldAnnotations.computeIfAbsent(fieldName,
                    s -> AnnotationUtils.getAnnotations(field, Validator.class));
            if (validators.size() > 0) {
                AnnotationRule annotationRule = new AnnotationRule(field, validators);
                CheckResult checkResult = annotationRule.check(val);
                setErrMessages(rowId, checkResult);
            }

            Repeatable repeatable = field.getAnnotation(Repeatable.class);
            if (Objects.nonNull(repeatable) && !repeatable.value()) {
                repeatableList.add(repeatable);
            }

            // 自定义校验
            customValidation(rowId, fieldName, val);
        }

        return row;
    }

    public List<T> repeatCheck(List<T> rows) {
        if (0 == repeatableList.size()) {
            return rows;
        }

        NonRepeatRule<T> nonRepeatRule = new NonRepeatRule<>(repeatableList, fields);
        for (int i = 0; i < rows.size(); i++) {
            CheckResult check = nonRepeatRule.check(rows.get(i));
            setErrMessages((long) i, check);
        }
        return rows;
    }

    @Override
    public abstract List<T> bizCheck(List<T> rows);

    /**
     * 注册校验规则
     * @param rule
     */
    protected void registerRule(@NotNull FieldRule rule) {
        this.RULE_MAP.put(rule.getFieldName(), rule);
    }

    protected void registerRule(@NotNull List<FieldRule> rules) {
        for (FieldRule rule : rules) {
            this.RULE_MAP.put(rule.getFieldName(), rule);
        }
    }

    protected void setErrMessages(Long rowId, CheckResult cr) {
        if (CheckResult.isPass(cr)) {
            return;
        }

        String errMsg = this.errMessages.get(rowId);
        if (Objects.nonNull(errMsg)) {
            errMsg += ";";
        } else {
            errMsg = "";
        }
        this.errMessages.put(rowId, errMsg + cr.getMsg());
    }

    private void customValidation(long rowId, String fieldName, Object val) {
        FieldRule fieldRule = RULE_MAP.get(fieldName);
        if (Objects.isNull(fieldRule)) {
            return;
        }
        CheckResult checkResult = fieldRule.check(val);
        setErrMessages(rowId, checkResult);
    }
}
