package com.sftcwl.brady.rule.impl;

import com.sftcwl.brady.annotation.Repeatable;
import com.sftcwl.brady.rule.CheckResult;
import com.sftcwl.brady.rule.Rule;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : NonRepeatRule
 * @description :
 * @date : 2024/1/13 5:25 下午
 **/
public class NonRepeatRule<T> implements Rule {

    private final List<Field> fieldList;
    private final Map<Repeatable, Set<String>> contentMap;

    public NonRepeatRule(List<Repeatable> repeatableList, List<Field> fieldList) {
        this.fieldList = fieldList;
        this.contentMap = new HashMap<>(4);
        for (Repeatable repeatable : repeatableList) {
            if (repeatable.value()) {
                continue;
            }
            contentMap.computeIfAbsent(repeatable, s -> new HashSet<>(8));
        }
    }

    public NonRepeatRule(Repeatable repeatable, List<Field> fieldList) {
        this.fieldList = fieldList;
        this.contentMap = new HashMap<>(1);
        if (repeatable.value()) {
            return;
        }
        contentMap.computeIfAbsent(repeatable, s -> new HashSet<>(8));
    }

    @SneakyThrows
    @Override
    public CheckResult check(Object val) {
        T row = (T) val;
        Set<Repeatable> repeatableSet = this.contentMap.keySet();
        Map<String, Field> fieldMap = this.fieldList.stream().collect(Collectors.toMap(Field::getName, v -> v));
        for (Repeatable repeatable : repeatableSet) {
            String[] fieldNames = repeatable.fieldNames();
            StringBuilder valBuilder = new StringBuilder();
            for (String fieldName : fieldNames) {
                Field field = fieldMap.get(fieldName);
                valBuilder.append((String) field.get(row));
            }
            Set<String> contents = contentMap.get(repeatable);
            if (contents.contains(valBuilder.toString())) {
                return CheckResult.failed("数据内容重复");
            } else {
                contents.add(valBuilder.toString());
            }
        }
        return CheckResult.pass();
    }


    /**
     * 自定义实现批量导入数据在数据库中的唯一性
     * @return
     */
    public CheckResult globalUnique() {
        return CheckResult.pass();
    }
}
