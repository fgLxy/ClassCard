package com.school.management.api.controller;

import com.google.gson.Gson;
import com.school.management.api.entity.Attendance;
import com.school.management.api.entity.Student;
import com.school.management.api.repository.AttendanceJpaRepository;
import com.school.management.api.repository.StudentJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/school/attendance")
public class AttendanceController {

    @Autowired
    AttendanceJpaRepository attendanceJpaRepository;

    @Autowired
    private StudentJpaRepository studentJpa;

    @PostMapping("/addAttendance")
    public Object addAttendance(String cardNum, String time, String classCode) {
       SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        Map<String, Object> data = new HashMap<>();
        Attendance attendance = attendanceJpaRepository.findByStudentNum(cardNum);
        time = time.substring(time.indexOf(" ") + 1);
        if (attendance != null) {
            try {
                if (timeFormat.parse(time).getTime() < timeFormat.parse("09:00:00").getTime()) {
                    attendance.setAttendanceArrivalTime(time);
                    attendance.setSignState(1);
                    attendance.setArrivalStatus(1);
                    attendance.setArrivalType(0);
                } else {
                    attendance.setArrivalStatus(1);
                    attendance.setArrivalType(1);
                    attendance.setSignState(1);
                    attendance.setAttendanceLeavingTime(time);
                }
                if (timeFormat.parse(time).getTime() > timeFormat.parse("18:00:00").getTime() && attendance.getSignState() != 0) {
                    attendance.setSignState(0);
                    attendance.setAttendanceLeavingTime(time);
                    attendance.setLevelStatus(1);
                    attendance.setLevelType(0);
                } else {
                    attendance.setLevelStatus(1);
                    attendance.setAttendanceLeavingTime(time);
                    attendance.setLevelType(1);
                }
                attendance = attendanceJpaRepository.saveAndFlush(attendance);
                data.put("studentName", attendance.getAttendanceStudentName());
                data.put("studentCardNum", attendance.getStudentNum());
                data.put("studentArrivalTime", attendance.getAttendanceArrivalTime().substring(attendance.getAttendanceArrivalTime().indexOf(" ") + 1));
                data.put("studentLeavingTime", attendance.getAttendanceLeavingTime().substring(attendance.getAttendanceLeavingTime().indexOf(" ") + 1));
                data.put("studentPhoto", attendance.getPhotoUrl());
                data.put("studentArrivalStatus", attendance.getArrivalStatus());
                data.put("studentArrivalType", attendance.getArrivalType());
                data.put("studentLeaveStatus", attendance.getLevelStatus());
                data.put("studentLeaveType", attendance.getLevelType());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "", data);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "学生卡号不正确");
        }
    }

    /**
     * 获取出勤学生列表
     */
    @PostMapping(value = "/attendanceStudentList")
    public Object getAllAttendanceStudents() {
        List<Attendance> list = attendanceJpaRepository.findAllBy();
        if (list.size() != 0) {
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", list);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败", list);
        }
    }

    /**
     * @param classCode 班级编号
     * @return 班级所有学生的考勤信息
     */
    @PostMapping("/getStudentAttendance")
    public Object getStudentAttendance(int classCode, @RequestParam(defaultValue = "1") int page) {
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", attendanceJpaRepository.findByClassCode(classCode, PageRequest.of(page - 1, 10)).getContent());
    }

    /**
     * @return 显示所有考勤
     */
    @GetMapping("/list")
    public Object listAll(@RequestParam(defaultValue = "1") int page) {
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", attendanceJpaRepository.findAll(PageRequest.of(page - 1, 8)));
    }

    @PostMapping("/delete")
    public Object delete(String id) {
        Attendance attendance = attendanceJpaRepository.findByAttendanceStudentId(Long.parseLong(id));
        if (attendance != null) {
            attendanceJpaRepository.delete(attendance);
            return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "删除失败");
    }

    @PostMapping("/deleteSome")
    public Object deleteSome(String Ids) {
        String[] repairIds = Ids.split(",");
        for (String repairId : repairIds) {
            attendanceJpaRepository.delete(attendanceJpaRepository.findByAttendanceStudentId(Long.parseLong(repairId)));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "批量删除成功");
    }

    @PostMapping("/add")
    public Object add(Attendance attendance) {
        if (attendance != null) {
            Student student = studentJpa.findByStudentName(attendance.getAttendanceStudentName());
            if (student != null) {
                attendance.setStudentNum(student.getStudentCardNum());
                return new JsonObjectResult(ResultCode.SUCCESS, "增加成功", attendanceJpaRepository.saveAndFlush(attendance));
            } else {
                return new JsonObjectResult(ResultCode.PARAMS_ERROR, "找不到学生信息，请核对后重试");
            }
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "增加失败，信息为空");
    }

    @RequestMapping("/update")
    public Object update(Attendance attendance) {
        System.out.println(new Gson().toJson(attendance));
        Attendance old = attendanceJpaRepository.findByAttendanceStudentId(attendance.getAttendanceStudentId());
        if (old != null) {
            try {
                return new JsonObjectResult(ResultCode.SUCCESS, "修改成功", attendanceJpaRepository.saveAndFlush(attendance));
            } catch (Exception e) {
                return new JsonObjectResult(ResultCode.PARAMS_ERROR, "未找到" + attendance.getAttendanceStudentName() + "学生信息");
            }
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "修改失败");

    }

    @PostMapping("/query")
    public Object query(String date) {
        return new JsonObjectResult(ResultCode.SUCCESS, "", attendanceJpaRepository.findByStudentNum(studentJpa.findByStudentName(date).getStudentCardNum()));
    }

    @PostMapping("/all")
    public Object all (int classCode) {
        return new JsonObjectResult(ResultCode.SUCCESS, "", attendanceJpaRepository.findByClassCode(classCode));
    }
}
