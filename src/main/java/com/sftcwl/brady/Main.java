package com.sftcwl.brady;

import com.sftcwl.brady.bean.Student;
import com.sftcwl.brady.checker.impl.StudentChecker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : Main
 * @description :
 * @date : 2024/1/13 2:37 下午
 **/
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello Checker!");
    }

    public void upload(List<Student> studentList) {

        Map<Long, String> errMsgMap = new HashMap<>();
        StudentChecker studentChecker = new StudentChecker(errMsgMap);
        for (Student student : studentList) {
            studentChecker.rowCheck(student);
        }

        studentChecker.repeatCheck(studentList);


        for (Map.Entry<Long, String> longStringEntry : errMsgMap.entrySet()) {
            String errMsg = String.format("第 %d 行数据, 错误信息: %s", longStringEntry.getKey(), longStringEntry.getValue());
            System.out.println(errMsg);
        }
    }
}
