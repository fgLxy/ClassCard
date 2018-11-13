package com.school.management.api.shiro;

import com.school.management.api.entity.Class;
import com.school.management.api.entity.*;
import com.school.management.api.repository.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.school.management.api.utils.RegexUtils.checkEmail;
import static com.school.management.api.utils.RegexUtils.checkMobile;

public class AuthRealm extends AuthorizingRealm {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserJpaRepository userJpa;

    @Autowired
    private TeacherJpaRepository teacherJpa;

    @Autowired
    private StudentJpaRepository studentJpa;

    @Autowired
    private DutyDayJpaRepository dutyDayJpa;

    @Autowired
    private ClassJpaRepository classJpa;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = userJpa.getByUserName((String) principals.getPrimaryPrincipal());
//        把principals放session中 key=userId value=principals
        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getUserId()), SecurityUtils.getSubject().getPrincipals());
//        为用户添加角色
        info.addStringPermission(user.getPermission().getPerName());
//        为用户添加权限
        for (Role role : user.getPermission().getRoles()) {
            info.addRole(role.getRoleName());
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken passwordToken = (UsernamePasswordToken) token;
        String userName = passwordToken.getUsername();
        User user = checkLogin(userName);

        if (user != null) {
            Session session = SecurityUtils.getSubject().getSession();
            session.setTimeout(86400000L);
            session.setAttribute("userName", user.getUserName());
            session.setAttribute("permission", user.getPermission());
            if (user.getIsTeacher() == 1) {
                Teacher teacher = teacherJpa.findByUserID(user.getUserId());
                Class aClass = classJpa.findByClassHeadmaster(teacher);
                session.setAttribute("headUrl", teacher.getHeadPhoto());
                if (user.getPermission().getPerId() == 1) {
                    session.setAttribute("principal", teacher.getTeacherName());
                } else {
                    session.setAttribute("teacher", teacher.getTeacherName());
                    session.setAttribute("teacherId", teacher.getTeacherId());
                    session.setAttribute("className", aClass.getClassName());
                    session.setAttribute("classCode", aClass.getClassroomCode());
                }
            } else if (user.getIsTeacher() == 0) {
                Student student = studentJpa.findByUserID(user.getUserId());
                Class clazz = classJpa.findByClassName(student.getStudentClassroom());
                List<DutyDay> dutyDays = dutyDayJpa.findByClassRoomCode(classJpa.findByClassName(student.getStudentClassroom()).getClassroomCode());
                for (DutyDay duty : dutyDays) {
                    if (duty.getDutyStudentName().equals(student.getStudentName())) {
                        session.setAttribute("duty", true);
                    }
                }
                session.setAttribute("headUrl", student.getStudentHeaderUrl());
                session.setAttribute("student", student.getStudentName());
                session.setAttribute("studentNum", student.getStudentNum());
                session.setAttribute("className", clazz.getClassName());
                session.setAttribute("classCode", clazz.getClassroomCode());
            }
            return new SimpleAuthenticationInfo(userName, user.getUserPassword(), getName());
        } else {
            return null;
        }
    }

    private User checkLogin(String userName) {
        User user;
        if (checkEmail(userName)) {
            user = userJpa.findByUserEmail(userName);
        } else if (checkMobile(userName)) {
            user = userJpa.findByUserMobilephoneNum(userName);
        } else {
            user = userJpa.getByUserName(userName);
        }
        return user;
    }
}
