package com.sftcwl.brady;

import cn.hutool.core.util.RandomUtil;
import com.sftcwl.brady.bean.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MainTest {

    @Test
    public void testMain() {
        List<Student> students = makeData();
        Main main = new Main();
        main.upload(students);
    }


    public List<Student> makeData() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Student student = new Student();
            student.setStuNo("20231212");
            //student.setStuNo(RandomUtil.randomString(6));
            student.setName(RandomUtil.randomString(21));
            student.setAge("5");
            student.setSex("3");
            student.setBirthDay("20240113");
            students.add(student);
        }

        return students;
    }
}