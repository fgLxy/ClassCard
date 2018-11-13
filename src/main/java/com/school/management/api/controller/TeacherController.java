package com.school.management.api.controller;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.school.management.api.entity.Course;
import com.school.management.api.entity.Teacher;
import com.school.management.api.entity.TeacherInfo;
import com.school.management.api.repository.ClassJpaRepository;
import com.school.management.api.repository.CourseJpaRepository;
import com.school.management.api.repository.TeacherInfoRepository;
import com.school.management.api.repository.TeacherJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/school/teacher")
public class TeacherController {

    private static final Gson GSON = new Gson();

    @Autowired
    private TeacherJpaRepository jpa;

    @Autowired
    private CourseJpaRepository courseJpa;

    @Autowired
    private TeacherInfoRepository infoJpa;

    @Autowired
    private ClassJpaRepository classJpa;

    @Autowired
    private RedisTemplate<String, Object> template;

    /**
     * @param teacherId 教师ID
     * @return 教师详情实体类 {@link TeacherInfo}
     */
    @PostMapping("/showInfo")
    public Object showInfo(int teacherId) {
        TeacherInfo info = jpa.findByTeacherId(teacherId).getTeacherInfo();
        if (info != null) {
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", info);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败");
        }
    }

    @GetMapping("/list")
    public Object list(@RequestParam(name = "page", defaultValue = "1") int page) {
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", jpa.findAll(PageRequest.of(page - 1, 8)));
    }

    /**
     * @param page
     * @return
     */
    @GetMapping("/listInfo")
    public Object listInfo(@RequestParam(name = "page", defaultValue = "1") int page, String className) {
        try {
            Teacher teacher = classJpa.findByClassName(className).getClassHeadmaster();
            Gson gson = new Gson();
            if (teacher != null) {
                Map<String, Object> data = gson.fromJson(gson.toJson(teacher), new TypeToken<Map<String, Object>>() {
                }.getType());
                List<Object> awardPhotos = new ArrayList<Object>();
                if (data.get("teacherInfo") instanceof LinkedTreeMap) {
                    data = (Map<String, Object>) data.get("teacherInfo");
                    Set<String> keySet = data.keySet();
                    for (String key : keySet) {
                        if (key.startsWith("award")) {
                            awardPhotos.add(data.get(key));
                        }
                    }
                    data.put("awardPhotos", awardPhotos);
                    data.put("teacherName", teacher.getTeacherName());
                    data.put("teacherPhoto", teacher.getHeadPhoto());
                }
                return new JsonObjectResult(ResultCode.SUCCESS, "", data);
            }
        } catch (Exception e) {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, e.getMessage());
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "请核对该班级是否有班主任");
    }

    @GetMapping("/toAdd")
    public Object toAdd() {
        List<Course> courses = courseJpa.findAll();
        List<Map<String, Object>> datas = new ArrayList<>();
        for (Course course : courses) {
            Map<String, Object> data = new HashMap<>();
            data.put("courseId", course.getCourseId());
            data.put("courseName", course.getCourseName());
            datas.add(data);
        }
        return datas;
    }

    @PostMapping("/add")
    public Object add(Teacher teacher, HttpServletRequest request) {
        try {
            TeacherInfo info = teacher.getTeacherInfo();
            teacher.setTeacherInfo(null);
            teacher.setCourse(teacher.getCourse());
            teacher.setHeadPhoto(ImgUtils.base64ToImg(teacher.getHeadPhoto(), UUID.randomUUID().toString() + ".jpg", teacher.getTeacherName()));
            teacher = jpa.saveAndFlush(teacher);
            info.setTeacherId(teacher.getTeacherId());
            teacher.setTeacherInfo(infoJpa.saveAndFlush(info));
            return new JsonObjectResult(ResultCode.SUCCESS, "添加成功");
        } catch (IOException e) {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "添加失败" + e.getMessage());
        }
    }

    @PostMapping("/update")
    public Object update(Teacher teacher) {
        Teacher old = jpa.findByTeacherId(teacher.getTeacherId());
        if (old != null) {
            TeacherInfo info = teacher.getTeacherInfo();
            info.setTeacherId(teacher.getTeacherId());
            infoJpa.saveAndFlush(info);
            teacher.setCourse(teacher.getCourse());
            Course course = courseJpa.findByCourseName(teacher.getCourse());
            if (course != null) {
                teacher.setCourse(course.getCourseName());
            } else {
                return new JsonObjectResult(ResultCode.PARAMS_ERROR, "未找到课程" + teacher.getCourse());
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "修改成功", jpa.saveAndFlush(teacher));
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "修改失败, 找不到待修改的教师ID");
    }

    @PostMapping("/delete")
    public Object delete(String id) {
        Teacher teacher = jpa.findByTeacherId(Long.parseLong(id));
        if (teacher != null) {
            jpa.delete(teacher);
            return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "删除失败");
    }

    @PostMapping("/deleteSome")
    public Object deleteSome(String Ids) {
        String[] teacherIds = Ids.split(",");
        for (String teacherId : teacherIds) {
            jpa.delete(jpa.findByTeacherId(Long.parseLong(teacherId)));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "批量删除成功");
    }

    @PostMapping("/query")
    public Object query(String date) {
        return new JsonObjectResult(ResultCode.SUCCESS, "", jpa.findByCourse(courseJpa.findByCourseName(date).getCourseName()));
    }

    @PostMapping("/all")
    public Object all(int classCode) {
        List<Map<String, Object>> datas = new ArrayList<>();
        Set<Map<String, Object>> mapSet = GSON.fromJson(GSON.toJson(classJpa.findByClassroomCode(classCode).getTeachers()), new TypeToken<Set<Map<String, Object>>>() {
        }.getType());
        for (Map<String, Object> map : mapSet) {
            Map<String, Object> data = new HashMap<>();
            data.put("teacherBlurb", ((LinkedTreeMap<String, Object>) map.get("teacherInfo")).get("teacherblurb"));
            data.put("teacherName", map.get("teacherName"));
            data.put("teacherPhoto", map.get("headPhoto"));
            datas.add(data);
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "", datas);
    }
}
