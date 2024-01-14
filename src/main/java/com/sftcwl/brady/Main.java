package com.sftcwl.brady;

import com.sftcwl.brady.bean.Student;
import com.sftcwl.brady.checker.impl.StudentChecker;
import com.sftcwl.brady.rule.impl.PhoneRule;

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
        // 创建错误信息Map
        Map<Long, String> errMsgMap = new HashMap<>();
        // 创建自定义Checker
        StudentChecker studentChecker = new StudentChecker(errMsgMap);
        // 自定义规则校验
        PhoneRule phoneRule = new PhoneRule("phoneNumber");
        // 把phoneRule注册到studentChecker上
        studentChecker.registerRule(phoneRule);

        // 模拟读取每行数据时进行的RowCheck
        for (Student student : studentList) {
            studentChecker.rowCheck(student);
        }

        // 模拟数据读取完毕之后进行数据重复检测
        studentChecker.repeatCheck(studentList);

        // 打印错误信息
        for (Map.Entry<Long, String> longStringEntry : errMsgMap.entrySet()) {
            String errMsg = String.format("第 %d 行数据, 错误信息: %s", longStringEntry.getKey(), longStringEntry.getValue());
            System.out.println(errMsg);
        }
    }
}
