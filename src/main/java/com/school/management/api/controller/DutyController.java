package com.school.management.api.controller;

import com.school.management.api.entity.DutyDay;
import com.school.management.api.entity.Student;
import com.school.management.api.repository.ClassJpaRepository;
import com.school.management.api.repository.DutyDayJpaRepository;
import com.school.management.api.repository.StudentJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.vo.ClassDuty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/school/duty")
public class DutyController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DutyDayJpaRepository jpa;

    @Autowired
    private StudentJpaRepository studentJpa;

    @Autowired
    private ClassJpaRepository classJpa;

    /**
     * @param classCode 班级编号
     * @return 该班级的值日表 {@link com.school.management.api.entity.DutyDay}
     */
    @PostMapping("/showDuty")
    public Object showDuty(int classCode) {
        List<DutyDay> dutyDays = jpa.findByClassRoomCode(classCode);
        List<String> list = null;
        Map<String, List<String>> map = new TreeMap<>();
        for (DutyDay dutyDay : dutyDays) {
            if (map.containsKey(dutyDay.getDutyDay())) {
                list.add(dutyDay.getDutyStudentName());
                map.put(dutyDay.getDutyDay(), list);
            } else {
                list = new ArrayList<>();
                list.add(dutyDay.getDutyStudentName());
                map.put(dutyDay.getDutyDay(), list);
            }
        }

        List<ClassDuty> classDuties = new ArrayList<>();
        for (String key : map.keySet()) {
            ClassDuty week = new ClassDuty();
            week.setState(Integer.parseInt(key));
            week.setStudents(map.get(key));
            classDuties.add(week);
        }

        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", classDuties);
    }

    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "1") int page, String classCode) {
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", jpa.findByClassRoomCode(Integer.parseInt(classCode), PageRequest.of(page - 1, 8, new Sort(Sort.Direction.ASC, "dutyDay"))));
    }

    @PostMapping("/delete")
    public Object delete(long id) {
        DutyDay duty = jpa.findByDutyId(id);
        if (duty != null) {
            jpa.delete(duty);
            return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "删除失败");
    }

    @PostMapping("/deleteSome")
    public Object deleteSome(String Ids) {
        String[] examIDs = Ids.split(",");
        for (String examId : examIDs) {
            jpa.delete(jpa.findByDutyId(Long.parseLong(examId)));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "批量删除成功");
    }

    @PostMapping("/add")
    public Object addExam(DutyDay dutyDay) {
        if (dutyDay != null) {
            return new JsonObjectResult(ResultCode.SUCCESS, "添加数据成功", jpa.save(dutyDay));
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "添加数据失败");
        }
    }

    @PostMapping("/update")
    public Object updateExam(DutyDay dutyDay) {
        DutyDay old = jpa.findByDutyId(dutyDay.getDutyId());
        if (old != null) {
            Student student = studentJpa.findByStudentNameAndStudentClassroom(dutyDay.getDutyStudentName(), classJpa.getNameByClassCode(dutyDay.getClassRoomCode()));
            if (student != null)
                return new JsonObjectResult(ResultCode.SUCCESS, "修改成功", jpa.saveAndFlush(dutyDay));
            else
                return new JsonObjectResult(ResultCode.PARAMS_ERROR, "该班级下没有这个学生");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "没有找到待修改的数据");
    }

    @PostMapping("/query")
    public Object query(String date, String classCode, @RequestParam(value = "page", defaultValue = "1") int page) {
        return new JsonObjectResult(ResultCode.SUCCESS, "", jpa.findByDutyDayAndClassRoomCode(date, Integer.parseInt(classCode), PageRequest.of(page - 1, 8)));
    }
}
