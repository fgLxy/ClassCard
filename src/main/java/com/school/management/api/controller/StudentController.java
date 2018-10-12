package com.school.management.api.controller;

import com.google.gson.Gson;
import com.school.management.api.entity.Student;
import com.school.management.api.entity.Teacher;
import com.school.management.api.repository.StudentJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/school/class")
public class StudentController {
    @Autowired
    StudentJpaRepository studentJpa;

    /**
     * 学生刷学生卡后，获取学生相关信息
     */
    @PostMapping(value = "/getStudentInfo")
    public Object getStudent(@RequestParam("card_code") int cardNum) {
        String cardCodeStr = String.valueOf(cardNum);

        if (cardNum != 0 && RegexUtils.checkIntegerPos(cardCodeStr)) {
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", studentJpa.getStudentByStudentCardNum(String.valueOf(cardNum)));
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败", studentJpa.getStudentByStudentCardNum(String.valueOf(cardNum)));
        }
    }

    /**
     * 获取所有学生信息
     */
    @GetMapping(value = "/getAllStudent")
    public Object getAllStudent(@RequestParam(defaultValue = "1", value = "page") int page) {
        Page<Student> list = studentJpa.findAll(
                new PageRequest(page - 1, 8, new Sort(Sort.Direction.DESC, "studentName"))
        );
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", list);
    }

    @PostMapping("/getClassStudent")
    public Object getClassStudent(@RequestParam(defaultValue = "1")int page, String className) {
        System.out.println(className);
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", studentJpa.findByStudentClassroom(className, PageRequest.of(page-1, 8)));
    }

    /**
     * @param student 待添加的学生信息
     * @return 添加是否成功
     */
    @PostMapping(value = "/addStudent")
    public Object addStudent(Student student) {
        student.setStudentNum((int) (Math.random() * 1000000));
        student.setStudentCardNum(String.valueOf(Math.random() * 1000000));
        return new JsonObjectResult(ResultCode.SUCCESS, "添加数据成功", studentJpa.save(student));
    }

    @PostMapping("/student/update")
    public Object update(Student student) {
        System.out.println(new Gson().toJson(student));
        Student old = studentJpa.findByStudentId(student.getStudentId());
        if (old != null) {
            return new JsonObjectResult(ResultCode.SUCCESS, "更改成功", studentJpa.saveAndFlush(student));
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "更改失败，找不到待修改的数据");
    }

    @PostMapping("/student/delete")
    public Object delete(String id) {
        Student student = studentJpa.findByStudentId(Long.parseLong(id));
        if (student != null) {
            studentJpa.delete(student);
            return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "删除失败");
    }

    @PostMapping("/student/deleteSome")
    public Object deleteSome(String Ids) {
        String[] teacherIds = Ids.split(",");
        for (String teacherId : teacherIds) {
            studentJpa.delete(studentJpa.findByStudentId(Long.parseLong(teacherId)));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "批量删除成功");
    }

    @PostMapping("/student/query")
    public Object query(String date, @RequestParam(defaultValue = "1")int page) {
        return new JsonObjectResult(ResultCode.SUCCESS, "", studentJpa.findByStudentClassroom(date, PageRequest.of(page - 1, 8)));
    }
}
