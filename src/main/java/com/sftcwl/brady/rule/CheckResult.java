package com.sftcwl.brady.rule;

import lombok.Getter;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : CheckResult
 * @description :
 * @date : 2024/1/13 3:09 下午
 **/
public class CheckResult {

    /**
     * 校验结果
     */
    private final boolean pass;

    /**
     * 错误信息
     */
    @Getter
    private String msg;

    private static final CheckResult PASS = new CheckResult();

    private CheckResult() {
        this.pass = true;
        this.msg = "";
    }

    private CheckResult(boolean pass) {
        this.pass = pass;
        this.msg = "";
    }

    public static CheckResult pass() {
        return PASS;
    }

    public static CheckResult failed(String errMsg) {
        CheckResult passResult = new CheckResult(false);
        passResult.setMsg(errMsg);
        return passResult;
    }

    public static boolean isPass(CheckResult cr) {
        return PASS.equals(cr) || cr.pass;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }
}
