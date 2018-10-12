package com.school.management.api.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.school.management.api.entity.Schedule;
import com.school.management.api.entity.ScheduleWeek;
import com.school.management.api.repository.CourseJpaRepository;
import com.school.management.api.repository.ScheduleJpaRepository;
import com.school.management.api.repository.ScheduleWeekJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.vo.CourseWeek;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/school/schedule")
public class ScheduleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ScheduleJpaRepository scheduleJpa;

    @Autowired
    private ScheduleWeekJpaRepository weekJpa;

    @Autowired
    private CourseJpaRepository courseJpa;

    /**
     * @param className 班级名称
     * @return 每日课程表实体类 {@link com.school.management.api.entity.Schedule}
     */
    @PostMapping(value = "/showEveryDay")
    public Object showEveryDay(String className) throws ParseException {
        List<Schedule> schedules = scheduleJpa.findByClassName(className);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("EE");
        List<Map<String, Object>> list = new ArrayList<>();
        int i = 0;
        if (schedules.size() != 0) {
            for (Schedule schedule : schedules) {
                if (schedule.getScheduleDate().equals(sdf.format(new Date()))) {
                    System.out.println(schedule.getCourseName());
                    Map<String, Object> map = new HashMap<>();
                    map.put("scheduleId", ++i);
                    map.put("period", schedule.getPeriod());
                    map.put("courseStarttime", format.format(format.parse(schedule.getCourseStarttime())));
                    map.put("instructor", schedule.getInstructor().getTeacherName());
                    map.put("className", schedule.getClassName());
                    map.put("lessonEndtime", format.format(format.parse(schedule.getLessonEndtime())));
                    map.put("lessonRepresentative", schedule.getLessonRepresentative());
                    map.put("scheduleDate", schedule.getScheduleDate());
                    map.put("courseName", schedule.getCourseName().getCourseName());
                    list.add(map);
                }
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", list);
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败", list);
    }

    /**
     * @param classCode 星期几
     * @return 每周课程实体类 {@link com.school.management.api.entity.ScheduleWeek}
     */
    @PostMapping(value = "/showWeek")
    public Object showWeek(int classCode) {

        List<ScheduleWeek> scheduleWeeks = weekJpa.findByClassCode(classCode);
        List<String> list = null;
        Map<String, List<String>> map = new TreeMap<>();
        for (ScheduleWeek week : scheduleWeeks) {
            if (map.containsKey(week.getWeekDay())) {
                list.add(week.getWeekCourse());
                map.put(week.getWeekDay(), list);
            } else {
                list = new ArrayList<>();
                list.add(week.getWeekCourse());
                map.put(week.getWeekDay(), null);
            }

        }

        List<CourseWeek> courseWeeks = new ArrayList<>();
        for (String key : map.keySet()) {
            CourseWeek week = new CourseWeek();
            week.setState(Integer.parseInt(key));
            week.setCourses(map.get(key));
            courseWeeks.add(week);
        }

        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", courseWeeks);
    }

    @GetMapping("/list")
    public Object list() {
        return showWeek(1);
    }

    @GetMapping("/listCourse")
    public Object listCourse() {
        return courseJpa.findAll();
    }

    @PostMapping("/update")
    public Object update(String courseWeeks, int classCode) {
        List<CourseWeek> courseWeeksList = new Gson().fromJson(courseWeeks, new TypeToken<List<CourseWeek>>() {
        }.getType());
        List<ScheduleWeek> scheduleWeeks = weekJpa.findByClassCode(classCode);
        for (int j = 0; j < scheduleWeeks.size(); j++) {
            ScheduleWeek scheduleWeek = scheduleWeeks.get(j);
            for (int i = 0; i < courseWeeksList.size(); i++) {
                CourseWeek courseWeek = courseWeeksList.get(i);
                if (Integer.parseInt(scheduleWeek.getWeekDay()) == courseWeek.getState()) {
                    scheduleWeek.setWeekCourse(courseWeek.getCourses().get(j % 10));
                }
            }
            weekJpa.saveAndFlush(scheduleWeek);
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "");
    }

    @PostMapping("/query")
    public Object query(Object query) {
        System.out.println(query);
        return new JsonObjectResult(ResultCode.SUCCESS);
    }
}
