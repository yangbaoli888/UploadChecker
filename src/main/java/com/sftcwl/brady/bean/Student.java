package com.sftcwl.brady.bean;

import com.sftcwl.brady.annotation.*;
import com.sftcwl.brady.annotation.Number;
import lombok.Data;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : Student
 * @description :
 * @date : 2024/1/13 9:50 下午
 **/
@Data
public class Student {
    @Repeatable(value = false, fieldNames = {"stuNo"})
    @Require
    private String stuNo;

    @Str(length = 20)
    @Require
    private String name;

    @Number(min = "6")
    private String age;

    @Number(min = "1", max = "2")
    private String sex;

    @Date(format = "yyyy/MM/dd HH:mm:ss")
    private String birthDay;
}
