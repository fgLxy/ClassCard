package com.school.management.api.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.google.gson.Gson;
import com.school.management.api.entity.*;
import com.school.management.api.entity.Class;
import com.school.management.api.netty.NettyServerHandler;
import com.school.management.api.repository.ClassJpaRepository;
import com.school.management.api.repository.StudentJpaRepository;
import com.school.management.api.repository.TeacherJpaRepository;
import com.school.management.api.repository.UserJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.utils.PhoneSend;
import com.school.management.api.utils.RegexUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Controller
@RequestMapping("/school")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserJpaRepository userJpa;

    @Autowired
    private TeacherJpaRepository teacherJpa;

    @Autowired
    private StudentJpaRepository studentJpa;

    @Autowired
    private ClassJpaRepository classJpa;

    /**
     * @param userName 登录账号
     * @param password 登录密码
     * @return permissionName: 角色名称
     * permissionId: 角色ID
     */
    @PostMapping("/login")
    @ResponseBody
    public Object login(String userName, String password, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Map<Object, Object> map = new HashMap<>();
        try {
            subject.login(token);
            Session session = SecurityUtils.getSubject().getSession();
            map.put("user", session.getAttribute("userName"));
            map.put("perName", ((Permission) session.getAttribute("permission")).getPerName());
            map.put("perId", ((Permission) session.getAttribute("permission")).getPerId());
            map.put("sessionId", session.getId());
            map.put("headUrl", session.getAttribute("headUrl"));
            if (session.getAttribute("teacher") != null || session.getAttribute("principal") != null) {
                if (((Permission) (session.getAttribute("permission"))).getPerId() == 1) {
                    map.put("principal", session.getAttribute("principal"));
                } else {
                    map.put("userName", userName);
                    map.put("teacher", session.getAttribute("teacher"));
                    map.put("teacherId", session.getAttribute("teacherId"));
                    map.put("className", session.getAttribute("className"));
                    map.put("classCode", session.getAttribute("classCode"));
                }
            } else if (session.getAttribute("student") != null) {
                map.put("userName", userName);
                map.put("studentNum", session.getAttribute("studentNum"));
                map.put("student", session.getAttribute("student"));
                map.put("className", session.getAttribute("className"));
                map.put("classCode", session.getAttribute("classCode"));
                map.put("duty", session.getAttribute("duty"));
            }
            if (RegexUtils.checkMobile(userName))
                NettyServerHandler.PHONENUM.add(userName);
            return new JsonObjectResult(ResultCode.SUCCESS, "登录成功", map);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonObjectResult(ResultCode.EXCEPTION, "登录失败，请检查用户名和密码");
        }
    }

    /**
     * @param user 用户实体类 {@link User}
     * @return true / false
     */
    @PostMapping(value = "/login/add")
    @ResponseBody
    public Object add(User user) {
        try {
            userJpa.save(user);
            return new JsonObjectResult(ResultCode.SUCCESS, "注册成功", true);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonObjectResult(ResultCode.EXCEPTION, e.getMessage(), false);
        }
    }

    /**
     * @param request see {@link HttpServletRequest}
     * @return 登录后返回用户信息，以及其用户对应的角色及其拥有的权限
     */
    @GetMapping("/showInfo")
    @ResponseBody
    public Object showInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            Map<Object, Object> map = new HashMap<>();
            map.put("userName", request.getSession().getAttribute("userName"));
            map.put("permission", request.getSession().getAttribute("permission"));
            map.put("teacher", request.getSession().getAttribute("teacher"));
            map.put("student", request.getSession().getAttribute("student"));
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", map);
        } else {
            return new JsonObjectResult(ResultCode.NOT_LOGIN, "登录信息已经过期，请重新登录");
        }
    }

    /**
     * @return 退出功能
     */
    @PostMapping("/logout")
    @ResponseBody
    public Object logout() {
        SecurityUtils.getSubject().logout();
        return new JsonObjectResult(ResultCode.SUCCESS, "退出成功");
    }

    @PostMapping("/loginApp")
    public Object loginApp(String phone, String code) {

        Map<String, Object> data = new HashMap<>();
//        data.put("permission", user.getPermission().getPerName());
//        data.put("userName", user.getUserName());

        return new JsonObjectResult(ResultCode.SUCCESS, "登录成功", data);
    }

    @PostMapping("/sendCode")
    public Object sendCod(String phoneNum) {
        SendSmsResponse response = PhoneSend.sendCode(phoneNum);
        if (response.getCode().equals("200")) return new JsonObjectResult(ResultCode.SUCCESS, response.getMessage());
        return new JsonObjectResult(ResultCode.EXCEPTION, response.getMessage());
    }

    @PostMapping("/enrol")
    public Object enrol(String phone, String reference, String code) {
        Map<String, Object> data = new HashMap<>();
        User user = new User();
        if (reference.startsWith("")) {
            Teacher teacher = teacherJpa.findByTeacherId(Long.parseLong(reference));
            data.put("teacher", teacher.getTeacherName());
            user.setIsTeacher(1);
            user.setUserMobilephoneNum(phone);
            user.setAddDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            user.setUserTeltphoneNum(phone);
            user = userJpa.saveAndFlush(user);
            teacher.setUserID(user.getUserId());
            teacherJpa.saveAndFlush(teacher);
//            user.setPermission();
        } else {
            Student student = studentJpa.findByStudentNum(Integer.parseInt(reference));
            data.put("student", student.getStudentName());
            data.put("classRoom", student.getStudentClassroom());
            user.setIsTeacher(1);
            user.setUserMobilephoneNum(phone);
            user.setAddDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            user.setUserTeltphoneNum(phone);
            user = userJpa.saveAndFlush(user);
            student.setUserID(user.getUserId());
            studentJpa.saveAndFlush(student);
//            user.setPermission();
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "注册成功", data);
    }

    @PostMapping("/forgetPassword")
    public Object forgetPassword(String userName, String oldPassword, String newPassword, String code) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = null;
        if (oldPassword != null && oldPassword.equals("")) {
            token = new UsernamePasswordToken(userName, oldPassword);
            try {
                subject.login(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (code != null && code.equals("")) {

        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "旧密码与验证码不能同时为空");
        }

        Map<Object, Object> map = new HashMap<>();
        User user = userJpa.getByUserName(userName);
        user.setUserPassword(newPassword);
        user = userJpa.saveAndFlush(user);
        map.put("userName", user.getUserName());
        map.put("permission", user.getPermission().getPerName());
        if (user.getIsTeacher() == 1) {
            map.put("teacher", teacherJpa.findByUserID(user.getUserId()).getTeacherName());
        } else {
            map.put("student", studentJpa.findByUserID(user.getUserId()).getStudentName());
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "", map);
    }

    @PostMapping("/changePhone")
    public Object changePhone(String oldPhone, String newPhone, String code) {
        User user = userJpa.findByUserMobilephoneNum(oldPhone);
        if (code != null && code.equals("") && newPhone != null && RegexUtils.checkMobile(newPhone)) {
            user.setUserMobilephoneNum(newPhone);
            user = userJpa.saveAndFlush(user);
            return new JsonObjectResult(ResultCode.SUCCESS, "更换绑定手机号成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "验证码输入错误， 请核对验证码是否正确，或者验证码已过期。");
    }

    @GetMapping("/hello")
    @ResponseBody
    public Object hello() {
//        E:\ClassManagement\ClassManagementSystemProject\out\production\classes
//        E:\ClassManagement\ClassManagementSystemProject\out\production\classes
//        E:\ClassManagement\ClassManagementSystemProject\out\production\classes\static\image\comment\photo_005.jpg
        try {
            System.out.println();
            System.out.println(ResourceUtils.getFile("classpath:").getPath());
            System.out.println();
            System.out.println(ResourceUtils.getURL("classpath:static/"));
            System.out.println();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "hello";
    }
}
