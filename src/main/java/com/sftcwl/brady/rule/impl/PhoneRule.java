package com.sftcwl.brady.rule.impl;

import com.sftcwl.brady.rule.CheckResult;
import com.sftcwl.brady.rule.FieldRule;

import java.util.regex.Pattern;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : PhoneRule
 * @description :
 * @date : 2024/1/14 2:31 下午
 **/
public class PhoneRule extends FieldRule {
    private static final Pattern COMPILE = Pattern.compile("^\\+?[1-9][0-9]{7,14}$");

    public PhoneRule(String fieldName) {
        super(fieldName);
    }

    @Override
    public CheckResult check(Object val) {

        // 验证手机号码
        boolean isMatch = COMPILE.matcher((String) val).find();
        if (isMatch) {
            return CheckResult.pass();
        }
        return CheckResult.failed("手机号码错误");
    }
}
