package com.sftcwl.brady.rule;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : Rule
 * @description :
 * @date : 2024/1/13 5:23 下午
 **/
public interface Rule {

    /**
     * 校验数据是否符合规范
     * @param val
     * @return
     */
    CheckResult check(Object val);
}
