package com.school.management.api.controller;

import com.school.management.api.entity.Achievement;
import com.school.management.api.repository.AchievementRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/school/achievement")
public class AchievementController {
    @Autowired
    AchievementRepository achievementRepository;

    /**
     *
     * */
    @PostMapping(value = "/allStudentAchievement")
    public Object getAllStudentAchievement(@RequestParam(value = "grade_type") String gradeType) {
        List<Achievement> achievementList = achievementRepository.allStudentAchievement(gradeType);

        if ("".equals(gradeType) && !RegexUtils.check1To4Integer(gradeType)) {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败", achievementList);
        } else if (!"".equals(gradeType) && !RegexUtils.check1To4Integer(gradeType)) {
            return new JsonObjectResult(ResultCode.NOT_PARAM, "获取数据失败", achievementList);
        } else {
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", achievementList);
        }
    }

    @PostMapping(value = "/getStudentAchievement")
    public Object getStudentAchievement(@RequestParam(value = "student_code") int studentCode, @RequestParam(value = "grade_type") String gradeType) {
        Achievement achievement = achievementRepository.getAchievementByStudentCodeAndGradeType(studentCode, gradeType);

        String studentCodeStr = String.valueOf(studentCode);

        if (studentCode == 0 && !RegexUtils.checkIntegerPos(studentCodeStr)) {
            return new JsonObjectResult(ResultCode.NOT_PARAM, "获取数据失败", achievement);
        } else if (studentCode != 0 && !RegexUtils.checkIntegerPos(studentCodeStr)) {
            return new JsonObjectResult(ResultCode.NOT_PARAM, "获取数据失败", achievement);
        } else if ("".equals(gradeType) && !RegexUtils.check1To4Integer(gradeType)) {
            return new JsonObjectResult(ResultCode.NOT_PARAM, "获取数据失败", achievement);
        } else {
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", achievement);
        }
    }
}
