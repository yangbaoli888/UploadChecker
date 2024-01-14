package com.sftcwl.brady.checker.impl;

import com.sftcwl.brady.bean.Student;

import java.util.List;
import java.util.Map;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : StudentChecker
 * @description :
 * @date : 2024/1/13 10:02 下午
 **/
public class StudentChecker extends AbstractChecker<Student> {

    public StudentChecker(Map<Long, String> errMsgMap) {
        super(errMsgMap);
    }

    @Override
    public List<Student> bizCheck(List<Student> rows) {
        return null;
    }
}
