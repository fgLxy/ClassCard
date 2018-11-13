package com.school.management.api.controller;


import com.google.gson.Gson;
import com.school.management.api.entity.Exam;
import com.school.management.api.entity.Teacher;
import com.school.management.api.netty.NettyChannelHandlerPool;
import com.school.management.api.netty.NettyServerHandler;
import com.school.management.api.repository.ClassJpaRepository;
import com.school.management.api.repository.ExamJpaRepository;
import com.school.management.api.repository.IpJpaRepository;
import com.school.management.api.repository.TeacherJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/school/exam")
public class ExamController extends NettyServerHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExamJpaRepository examJpa;

    @Autowired
    private TeacherJpaRepository teacherJpa;

    @Autowired
    private ClassJpaRepository classJpa;

    @Autowired
    private IpJpaRepository ipJpa;

    @GetMapping("/listAll")
    public Object listAll(@RequestParam(name = "page", defaultValue = "1") int page) {
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", examJpa.findAll(PageRequest.of(page - 1, 8, new Sort(Sort.Direction.ASC, "examDate"))));
    }

    /**
     * @param examRoom 考场编号
     * @return 实体类 {@link Exam}
     */
    @PostMapping("/showSurvey")
    public Object showExamInfo(String examRoom) {
        List<Exam> exam = examJpa.findByExamRoom(examRoom);
        if (exam != null) {
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", exam);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败");
        }
    }

    /**
     * @param exam 待添加的考试信息
     * @return 添加是否成功
     */
    @PostMapping("/add")
    public Object addExam(Exam exam) {
        if (exam != null) {
            String[] teachers = exam.getTeacherId().split("，");
            Map<String, Object> datas = new HashMap<>();
            Map<String, Object> map = new HashMap<>();
            String startTime = exam.getExamStartTime();
            String endTime = exam.getExamEndTime();
            String[] teacherNames = new String[teachers.length];
            for (int i = 0; i < teachers.length; i++) {
                Teacher teacher = teacherJpa.findByTeacherName(teachers[i]);
                if (teacher != null) {
                    teacherNames[i] = teacher.getTeacherName();
                } else {
                    return new JsonObjectResult(ResultCode.PARAMS_ERROR, "找不到监考老师的信息，请核对后重试。");
                }
            }
            int hasClass = classJpa.hasClass(exam.getExamPlace());
            if (hasClass <= 0) {
                return new JsonObjectResult(ResultCode.PARAMS_ERROR, "本校没有这个考试地点：" + exam.getExamPlace());
            }
            try {
                if (checkExam(exam)) {
                    return new JsonObjectResult(ResultCode.PARAMS_ERROR, "您添加的考试不符合规则");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            datas.put("examTeacher", teacherNames);
            datas.put("examTime", startTime.substring(startTime.indexOf(" ") + 1) + "-" + endTime.substring(startTime.indexOf(" ") + 1));
            datas.put("examCourse", exam.getExamSubject());
            datas.put("examCode", exam.getExamCode());
            map.put("type", 2);
            map.put("data", datas);
            map.put("message", "处理数据成功");
            for (String ip : IpList) {
                Channel channel = NettyChannelHandlerPool.channelGroup.find((ChannelId) nettyMap.get(ip));
                if (channel != null) {
                    ChannelFuture future = channel.writeAndFlush(new Gson().toJson(map));
                }
            }
            String teacheres = Arrays.toString(teacherNames);
            exam.setTeacherId(teacheres.substring(1, teacheres.length() - 1));
            return new JsonObjectResult(ResultCode.SUCCESS, "添加数据成功", examJpa.save(exam));
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "添加数据失败");
        }
    }

    @PostMapping("/update")
    public Object updateExam(Exam exam) {
        Exam old = examJpa.findByExamId(exam.getExamId());
        if (old != null) {
            return new JsonObjectResult(ResultCode.SUCCESS, "修改成功", examJpa.saveAndFlush(exam));
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "没有找到待修改的数据");
    }

    @PostMapping("/delete")
    public Object delete(long examId) {
        Exam exam = examJpa.findByExamId(examId);
        if (exam != null) {
            examJpa.delete(exam);
            return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "删除失败");
    }

    @PostMapping("/deleteSome")
    public Object deleteSome(String examIds) {
        String[] examIDs = examIds.split(",");
        for (String examId : examIDs) {
            examJpa.delete(examJpa.findByExamId(Long.parseLong(examId)));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "批量删除成功");
    }

    @PostMapping("/query")
    public Object query(String date, @RequestParam(defaultValue = "1") int page) {
        return new JsonObjectResult(ResultCode.SUCCESS, "查询数据成功", examJpa.findByExamDateLike(date + "%", PageRequest.of(page-1, 8)));
    }

    @PostMapping("/nearlingExam")
    public Object nearlingExam() {
        List<Exam> exams = examJpa.findAll(new Sort(Sort.Direction.ASC, "examDate"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (Exam exam : exams) {
            try {
                if (new Date(System.currentTimeMillis()).getTime() < format.parse(exam.getExamDate()).getTime()) {
                    return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", exam);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "最近无考试安排");
    }

    private boolean checkExam(Exam exam) throws ParseException {
        List<Exam> examList = examJpa.findByExamRoom(exam.getExamRoom());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long addedEndTime = sdf.parse(exam.getExamEndTime()).getTime();
        long addedStartTime = sdf.parse(exam.getExamStartTime()).getTime();
        for (Exam e : examList) {
            long endTime = sdf.parse(e.getExamEndTime()).getTime();
            long startTime = sdf.parse(e.getExamStartTime()).getTime();
            if (addedEndTime < startTime && startTime < addedStartTime) {
                return false;
            } else if (addedStartTime < endTime && endTime < addedEndTime) {
                return false;
            }
        }
        return true;
    }
}
