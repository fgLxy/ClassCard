package com.school.management.api.controller;

import com.google.gson.Gson;
import com.school.management.api.entity.Grade;
import com.school.management.api.entity.Student;
import com.school.management.api.repository.ClassJpaRepository;
import com.school.management.api.repository.GradeJpaRepository;
import com.school.management.api.repository.StudentJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.*;

@RestController
@RequestMapping("/school/grade")
public class GradeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GradeController.class);

    @Autowired
    private GradeJpaRepository gradeJpa;

    @Autowired
    private StudentJpaRepository studentJpa;

    @Autowired
    private ClassJpaRepository classJpa;

    @Autowired
    private RedisTemplate<String, Object> template;

    /**
     * @param page 当前页数
     * @return 分页后的成绩列表
     */
    @PostMapping(value = "/showAll")
    public Object showAll(@RequestParam(value = "page", defaultValue = "1") int page, String classRoom) {
        List<Student> students = studentJpa.findByStudentClassroom(classRoom, PageRequest.of(page - 1, 8)).getContent();
        List<Map<String, Object>> datas = new ArrayList<>();
        for (Student student : students) {
            Map<String, Object> data = new HashMap<>();
            data.put("studentId", student.getStudentId());
            data.put("headerUrl", student.getStudentHeaderUrl());
            data.put("studentName", student.getStudentName());
            List<Grade> grades = student.getGrades();
            int size = grades.size() - 1;
            if (size != -1) {
                data.put("gradeTime", grades.get(grades.size() - 1).getGradeDate());
                data.put("gradeType", grades.get(grades.size() - 1).getGradeType());
                data.put("gradeId", grades.get(grades.size() - 1).getGradeId());
            }
            int count = 0;
            for (Grade grade : grades) {
                count += grade.getGradeSorce();
            }
            if (grades.size() != 0) {
                data.put("gradeAvg", count / grades.size());
            } else {
                data.put("gradeAvg", count);
            }
            data.put("info", grades);
            datas.add(data);
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", datas);
    }

    /**
     * @param grade 待更新的通知
     * @return 更新后的通知
     */
    @PostMapping("/update")
    public Object updateNotice(Grade grade) {
        Grade old = gradeJpa.findByGradeId(grade.getGradeId());
        if (old != null) {
            return new JsonObjectResult(ResultCode.SUCCESS, "修改成功", grade);
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "修改失败，找不到待修改的数据");
    }

    /**
     * @param grade 待添加的通知
     * @return 添加成功后的通知
     */
    @PostMapping("/add")
    public Object addNotice(Grade grade) {
        System.out.println(new Gson().toJson(grade));
        Student student = studentJpa.findByStudentNum(grade.getStudentId());
        if (student != null) {
            return new JsonObjectResult(ResultCode.SUCCESS, "增加成功", gradeJpa.saveAndFlush(grade));
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "增加失败，没有这个学生。");
        }
    }

    /**
     * @param Id 通知ID
     * @return true / false
     */
    @PostMapping("/delete")
    public Object removeNotice(long Id) {
        Grade grade = gradeJpa.findByGradeId(Id);
        if (grade != null) {
            gradeJpa.delete(grade);
            return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "删除失败，待删除的数据已经被删除。");
    }

    /**
     * @param Ids 待删除的通知ID
     * @return 是否删除成功
     */
    @PostMapping("/deleteSome")
    public Object removeSomeNotice(String Ids) {
        String[] ids = Ids.split(",");
        for (String gradeId : ids) {
            gradeJpa.delete(gradeJpa.findByGradeId(Long.parseLong(gradeId)));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
    }

    /**
     * @param date 日期
     * @return 根据日期来查询得到的部分通知
     */
    @PostMapping("/queryByTime")
    public Object queryByTime(String date) {
        return new JsonObjectResult(ResultCode.SUCCESS, "查询成功", gradeJpa.findByGradeDateLike(date + "%"));
    }

    @PostMapping("/asStudent")
    public Object asStudent(String studentNum, String classCode) {
        try {
            if (studentNum != null && classCode == null) {
                List<Map<String, Object>> datas = new ArrayList<>();
                for (Map<String, Object> map : gradeJpa.getAllDate()) {
                    Map<String, Object> data = new HashMap<>();
                    List<Grade> gradeSet = gradeJpa.getByStudentIdAndGradeDateLike(Integer.parseInt(studentNum), map.get("grade_date") + "%");
                    data.put("date", map.get("grade_date"));
                    data.put("gradeName", map.get("grade_type"));
                    int count = 0;
                    for (Grade grade : gradeSet) {
                        count += grade.getGradeSorce();
                    }
                    List<Object> list = new ArrayList<>();
                    Map<String, Object> classGrade = new HashMap<>();
                    Student student = studentJpa.findByStudentNum(Integer.parseInt(studentNum));
                    classGrade.put("studentName", student.getStudentName());
                    classGrade.put("studentNum", student.getStudentNum());
                    classGrade.put("count", count);
                    list.add(classGrade);
                    data.put("classGrade", list);
                    datas.add(data);
                }
                return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", datas);
            }
            if (classCode != null && studentNum == null) {
                Set<Student> studentSet = classJpa.findByClassroomCode(Integer.parseInt(classCode)).getStudents();
                List<Map<String, Object>> dates = gradeJpa.getAllDate();
                List<Map<String, Object>> datas = new ArrayList<>();
                for (Map<String, Object> map : dates) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("date", map.get("grade_date"));
                    data.put("gradeName", map.get("grade_type"));
                    List<Map<String, Object>> classGrade = new ArrayList<>();
                    for (Student student : studentSet) {
                        Map<String, Object> studentGrade = new HashMap<>();
                        List<Grade> gradeList = student.getGrades();
                        studentGrade.put("studentName", student.getStudentName());
                        studentGrade.put("studentNum", student.getStudentNum());
                        int count = 0;
                        for (Grade grade : gradeList) {
                            count += grade.getGradeSorce();
                        }
                        studentGrade.put("count", count);
                        classGrade.add(studentGrade);
                    }
                    data.put("classGrade", classGrade);
                    datas.add(data);
                }
                return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", datas);
            }
        } catch (Exception e) {
            return new JsonObjectResult(ResultCode.EXCEPTION, e.getMessage());
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "班级编号和学生好不能同时  存在或不存在");
    }

    @PostMapping("/showInfo")
    public Object showInfo(String studentNum, String date, HttpServletRequest request) {
        List<Grade> grades = gradeJpa.findByStudentIdAndGradeDate(Integer.parseInt(studentNum), date);
        Map<String, Object> datas = new HashMap<>();
        List<Map<String, Object>> gradeInfo = new ArrayList<>();
        datas.put("headUrl", studentJpa.findByStudentNum(Integer.parseInt(studentNum)).getStudentHeaderUrl());
        datas.put("gradeName", grades.get(0).getGradeType());
        int count = 0;
        for (Grade grade : grades) {
            Map<String, Object> data = new HashMap<>();
            data.put("course", grade.getGradeCourse());
            data.put("sorce", grade.getGradeSorce());
            data.put("rank", grade.getGradeRank());
            count += grade.getGradeSorce();
            gradeInfo.add(data);
        }
        datas.put("count", count);
        datas.put("gradeInfo", gradeInfo);
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", datas);
    }
}
