package com.sftcwl.brady.rule;

import lombok.Getter;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : FieldRule
 * @description :
 * @date : 2024/1/13 3:09 下午
 **/
public abstract class FieldRule implements Rule {
    @Getter
    public final String fieldName;

    public FieldRule(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * 校验数据是否符合规范
     * @param val
     * @return
     */
    @Override
    public abstract CheckResult check(Object val);
}
