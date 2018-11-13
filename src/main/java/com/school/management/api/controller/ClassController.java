package com.school.management.api.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.school.management.api.entity.*;
import com.school.management.api.entity.Class;
import com.school.management.api.netty.NettyChannelHandlerPool;
import com.school.management.api.netty.NettyServerHandler;
import com.school.management.api.repository.*;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.utils.ImgUtils;
import com.school.management.api.vo.AddedClass;
import com.school.management.api.vo.ClassName;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.util.CharsetUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/school/class")
public class ClassController extends NettyServerHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public IpJpaRepository ipJpa;

    @Autowired
    public ClassJpaRepository classJpa;

    @Autowired
    private AttendanceJpaRepository attJpa;

    @Autowired
    private TeacherJpaRepository teacherJpa;

    @Autowired
    private StudentJpaRepository studentJpa;

    @Autowired
    private NoticeJpaRepository noticeJpa;

    @Autowired
    private GradeJpaRepository gradeJpa;

    @Autowired
    private RedisTemplate<String, Object> template;

    /**
     * @return 分页后的全部班级信息
     */
    @GetMapping("/listAll")
    public Object showAll(@RequestParam(name = "page", defaultValue = "1") int page, String className) {
        Page<Class> ips = null;
        if (className == null || className.equals("")) {
            ips = classJpa.findAll(PageRequest.of(page - 1, 8, new Sort(Sort.Direction.ASC, "className")));
        } else if (!className.equals("") && !className.equals("undefind")) {
            ips = classJpa.findByClassNameLike(className + "%", PageRequest.of(page - 1, 8, new Sort(Sort.Direction.ASC, "className")));
        }

        List<Map<String, Object>> jsons = new ArrayList<>();
        for (Class classes : ips) {
            Map<String, Object> json = new HashMap<>();
            json.put("classId", classes.getClassId());
            json.put("className", classes.getClassName());
            json.put("classStudentTotal", classes.getClassStudentTotal());
            json.put("classMaxStudent", classes.getClassMaxStudent());
            json.put("classroomCode", classes.getClassroomCode());
            json.put("classScore", classes.getClassScore());
            if (classes.getClassHeadmaster() != null) {
                json.put("classHeadmaster", classes.getClassHeadmaster().getTeacherName());
            } else {
                json.put("classHeadmaster", null);
            }
            if (classes.getClassMonitor() != null) {
                json.put("classMonitor", classes.getClassMonitor().getStudentName());
            } else {
                json.put("classMonitor", null);
            }
            json.put("quantifies", 10 - classes.getQuantifies().size());
            jsons.add(json);
        }
        return new JsonObjectResult(ResultCode.SUCCESS, ips.getTotalPages() + "", jsons);
    }

    /**
     * @param IP 班牌所在的IP
     * @return classID: 班级ID
     * className: 班级名称
     * classCode: 班级编号
     * <p>
     * 在给电视端显示班级名称的同时将，终端IP存储在一个IPList中
     */
    @PostMapping("/showName")
    public Object showClassName(String IP) {
        Class aClass;
        Gson gson = new Gson();
        ClassName name = new ClassName();
        if (!IP_SCHEDULE.containsKey(IP)) {
            aClass = ipJpa.findByIp(IP).getClassId();
        } else {
            aClass = gson.fromJson(template.opsForValue().get(String.valueOf(CLASSCODE_IP.get(IP))).toString(), new TypeToken<Class>() {
            }.getType());
        }
        if (aClass != null) {
            name.setClassID(aClass.getClassId());
            name.setClassName(aClass.getClassName());
            name.setClassCode(aClass.getClassroomCode());
            Set<Schedule> scheduleSet = new TreeSet<>();
            for (Schedule schedule : aClass.getSchedules()) {
                if (schedule.getScheduleDate()
                        .equals(new SimpleDateFormat("EE").format(new Date(System.currentTimeMillis())))) {
                    ((TreeSet<Schedule>) scheduleSet).add(schedule);
                }
            }
            ValueOperations<String, Object> operations = template.opsForValue();
            Object redisDate = operations.get(String.valueOf(aClass.getClassroomCode()));
            if (redisDate == null) {
                operations.set(String.valueOf(aClass.getClassroomCode()), gson.toJson(aClass));
            } else if (!redisDate.equals(gson.toJson(aClass))) {
                operations.getAndSet(String.valueOf(aClass.getClassroomCode()), gson.toJson(aClass));
            }
            CLASSCODE_IP.put(IP, aClass.getClassroomCode());
            IpList.add(IP);
            if (IP_SCHEDULE.containsKey(IP) && !IP_SCHEDULE.get(IP).equals(scheduleSet)) {
                IP_SCHEDULE.remove(IP);
            }
            IP_SCHEDULE.put(IP, scheduleSet);
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", name);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败，请核对IP地址是否正确");
        }
    }

    /**
     * @param classCode 班牌所在的IP
     * @return classHeadMaster:班主任
     * monitor:班长
     * studentTotal:班级总人数
     * signInCount:已签到人数
     * lateCount:迟到人数
     * holidayCount:请假人数
     * moralEducation:今日德育
     * score:班级评分
     */
    @PostMapping("/showInfo")
    public Object showClassInfo(Integer classCode) {
        Map<String, Object> map = new HashMap<>();
        Class aClass =
                new Gson().fromJson(template.opsForValue().get(String.valueOf(classCode)).toString(), new TypeToken<Class>() {
                }.getType());
        if (aClass != null) {
            Set<Student> students = aClass.getStudents();
            map.put("classHeadMaster", aClass.getClassHeadmaster().getTeacherName());
            map.put("teacherId", aClass.getClassHeadmaster().getTeacherId());
            if (aClass.getClassMonitor() != null) {
                map.put("monitor", aClass.getClassMonitor().getStudentName());
            } else {
                map.put("monitor", "该班级暂无班长一职");
            }
            map.put("studentTotal", aClass.getClassStudentTotal());
            map.put("holidayCount", aClass.getClassHolidayCount());
            map.put("moralEducation", aClass.getClassMoralEducation());
            map.put("score", aClass.getClassScore());
            String arrivalTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "%";
            for (Student student : students) {
                Attendance attendance = attJpa.findByStudentNumAndAttendanceArrivalTimeLike(student.getStudentCardNum(), arrivalTime + '%');
                Integer signInCount = aClass.getClassSignInCount();
                Integer lateCount = aClass.getClassLateCount();
                if (attendance != null) {
                    map.put("signInCount", signInCount++);
                    map.put("lateCount", lateCount);
                } else {
                    map.put("signInCount", signInCount);
                    map.put("lateCount", lateCount++);
                }
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", map);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败");
        }
    }

    /**
     * @param classCode 班牌所在的IP
     * @return {@link com.school.management.api.entity.Quantify}
     */
    @PostMapping("/showMoral")
    public Object showMoral(Integer classCode) {
        Class aClass =
                new Gson().fromJson(template.opsForValue().get(String.valueOf(classCode)).toString(), new TypeToken<Class>() {
                }.getType());
        Set<Quantify> quantifies = aClass.getQuantifies();
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (quantifies.size() != 0) {
            for (Quantify quantify : quantifies) {
                Map<String, Object> map = new HashMap<>();
                int type = quantify.getQuantifyType();
                List<String> list = new ArrayList<>();
                switch (type) {
                    case 1:
                        map.put("1", quantify.getQuantifyRemark());
                        break;
                    case 2:
                        map.put("2", quantify.getQuantifyRemark());
                        break;
                    case 3:
                        map.put("3", quantify.getQuantifyRemark());
                        break;
                    default:
                        break;
                }
                list.add(quantify.getQuantifyPhotoUrl());
                list.add(quantify.getQuantifyPhotoUrl2());
                list.add(quantify.getQuantifyPhotoUrl3());
                map.put("photo", list);
                mapList.add(map);
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", mapList);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败");
        }
    }

    /**
     * @return 在班牌界面显示一条通知
     */
    @PostMapping("/showNotice")
    public Object showNotice() {
        List<Notice> notices = noticeJpa.findAll();
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", notices.get(notices.size() - 1));
    }

    /**
     * @param type      相册类别： 1—班级风采； 2—作品展示
     * @param classCode 班级编号
     * @return 当前类别下的相册
     */
    @PostMapping("/showAlbum")
    public Object showAlbum(int type, int classCode) {
        Class aClass =
                new Gson().fromJson(template.opsForValue().get(String.valueOf(classCode)).toString(), new TypeToken<Class>() {
                }.getType());
        Set<Album> albums = aClass.getAlbum();
        List<String> list = new ArrayList<>();
        for (Album album : albums) {
            if (type == album.getAlbumType()) {
                list.add(album.getPhotoUrl());
            }
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", list);
    }

    /**
     * @param classCode 班级编号
     * @return 显示部分的班级风采
     */
    @PostMapping("/showClassStyle")
    public Object showClassStyle(int classCode) {
        Class aClass =
                new Gson().fromJson(template.opsForValue().get(String.valueOf(classCode)).toString(), new TypeToken<Class>() {
                }.getType());
        Set<Album> albums = aClass.getAlbum();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Album album : albums) {
            if (1 == album.getAlbumType()) {
                int count = 0;
                if (count < 3) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("photoUrl", album.getPhotoUrl());
                    map.put("describe", album.getDescribe());
                    list.add(map);
                }
                count++;
            }
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", list);
    }

    /**
     * @param classCode 班级编号
     * @return 显示部分作品
     */
    @PostMapping("/showWorks")
    public Object showWorks(int classCode) {
        Class aClass =
                new Gson().fromJson(template.opsForValue().get(String.valueOf(classCode)).toString(), new TypeToken<Class>() {
                }.getType());
        Set<Album> albums = aClass.getAlbum();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Album album : albums) {
            if (2 == album.getAlbumType()) {
                int count = 0;
                if (count < 3) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("photoUrl", album.getPhotoUrl());
                    map.put("describe", album.getDescribe());
                    list.add(map);
                }
                count++;
            }
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", list);
    }

    /**
     * @param classCode 班级编号
     * @return 班级荣誉榜
     */
    @PostMapping("/showGrade")
    public Object showGrade(int classCode) {
        Class aClass =
                new Gson().fromJson(template.opsForValue().get(String.valueOf(classCode)).toString(), new TypeToken<Class>() {
                }.getType());
        Set<Student> students = aClass.getStudents();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Student student : students) {
            for (int i = (student.getGrades().size() - 1); i > -1; i--) {
                Grade grade = student.getGrades().get(i);
                if (grade.getGradeRank() <= 3 && grade.getGradeRank() > 0) {
                    if ((i + 1) == student.getGrades().size()) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("studentName", student.getStudentName());
                        map.put("studentRank", grade.getGradeRank());
                        map.put("gradeScore", grade.getGradeSorce());
                        map.put("studentHeadUrl", student.getStudentHeaderUrl());
                        list.add(map);
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("lastStudentName", student.getStudentName());
                        map.put("lastStudentRank", grade.getGradeRank());
                        map.put("lastGradeScore", grade.getGradeSorce());
                        map.put("lastStudentHeadUrl", student.getStudentHeaderUrl());
                        list.add(map);
                    }
                }
            }
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", list);
    }

    /**
     * @param addedClass 封装了一些添加班级时候必须有的字段
     * @return 添加成功与否
     */
    @PostMapping("/addClass")
    public Object addClass(AddedClass addedClass) {
        try {
            Class aClass = new Class();
            aClass.setClassDate(addedClass.getClassDate());
            aClass.setClassroomCode(addedClass.getClassroomCode());
            aClass.setClassMoralEducation(addedClass.getClassMoralEducation());
            aClass.setClassName(addedClass.getClassName());
            aClass.setClassStudentTotal(addedClass.getClassStudentTotal());
            Student student = studentJpa.findByStudentNameAndStudentClassroom(addedClass.getClassMonitor(), addedClass.getClassName());
            Teacher teacher = teacherJpa.findByTeacherName(addedClass.getClassHeadmaster());
            if (student != null) {
                aClass.setClassMonitor(student);
            } else {
                aClass.setClassMonitor(null);
            }
            if (Integer.parseInt(addedClass.getClassScore()) <= 5) {
                aClass.setClassScore(Integer.parseInt(addedClass.getClassScore()));
            } else {
                return new JsonObjectResult(ResultCode.PARAMS_ERROR, "班级评分不能超过整数5");
            }
            if (teacher != null) {
                aClass.setClassHeadmaster(teacher);
            } else {
                return new JsonObjectResult(ResultCode.PARAMS_ERROR, "添加失败，找不到教师信息。请核对该教师是否还在职！");
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "添加成功", classJpa.saveAndFlush(aClass));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new JsonObjectResult(ResultCode.EXCEPTION, "班级名称已存在");
        }
    }

    /**
     * @param addedClass 更改的数据
     * @return true / false
     */
    @PostMapping("/updateClass")
    public Object updateClass(AddedClass addedClass) {
        Class aClass = classJpa.findByClassroomCode(addedClass.getClassroomCode());
        if (aClass != null) {
            aClass.setClassStudentTotal(addedClass.getClassStudentTotal());
            aClass.setClassScore(Integer.parseInt(addedClass.getClassScore()));
            Teacher teacher = teacherJpa.findByTeacherName(addedClass.getClassHeadmaster());
            Student student = studentJpa.findByStudentNameAndStudentClassroom(addedClass.getClassMonitor(), addedClass.getClassName());
            if (teacher != null) {
                aClass.setClassHeadmaster(teacher);
            } else {
                return new JsonObjectResult(ResultCode.PARAMS_ERROR, "查询不到你输入的教师，请核对后再试。");
            }
            if (student != null) {
                aClass.setClassMonitor(student);
            } else {
                return new JsonObjectResult(ResultCode.PARAMS_ERROR, "没有找到你输入的班长，请核对后重试");
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "更改数据成功", classJpa.saveAndFlush(aClass));
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "查询不到你要更改的数据，请核对后再试。");
        }
    }

    /**
     * @param classCode 班牌IP地址
     * @return 根据ip地址查询得到的某个班级信息
     */
    @PutMapping("/toUpdate")
    public Object toUpdate(Integer classCode) {
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", classJpa.findByClassroomCode(classCode));
    }

    /**
     * @param classId IP
     * @return 根据班牌IP地址删除某个班级信息
     */
    @PostMapping("/deleteClass")
    public Object deleteClass(int classId) {
        try {
            classJpa.delete(classJpa.findByClassId((long) classId));
            return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return new JsonObjectResult(ResultCode.EXCEPTION, e.getMessage());
        }
    }

    /**
     * @param classIds 传过来的班级编号字符串，
     * @return 删除多个班级是否成功
     */
    @PostMapping("/deleteSomeClass")
    public Object deleteSomeClass(String classIds) {
        String[] ids = classIds.split(",");
        for (String id : ids) {
            classJpa.delete(classJpa.findByClassId(Long.parseLong(id)));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
    }

    /**
     * @param classCode 班级编号
     * @return 删除班级是否成功
     */
    @PostMapping("/findByClassCode")
    public Object findByClassCode(String classCode) {
        Class classes =
                new Gson().fromJson(template.opsForValue().get(String.valueOf(classCode)).toString(), new TypeToken<Class>() {
                }.getType());
        Map<String, Object> json = new HashMap<>();
        json.put("classId", classes.getClassId());
        json.put("className", classes.getClassName());
        json.put("classStudentTotal", classes.getClassStudentTotal());
        json.put("classMaxStudent", classes.getClassMaxStudent());
        json.put("classroomCode", classes.getClassroomCode());
        json.put("classScore", classes.getClassScore());
        if (classes.getClassHeadmaster() != null) {
            json.put("classHeadmaster", classes.getClassHeadmaster().getTeacherName());
        } else {
            json.put("classHeadmaster", null);
        }
        if (classes.getClassMonitor() != null) {
            json.put("classMonitor", classes.getClassMonitor().getStudentName());
        } else {
            json.put("classMonitor", null);
        }
        json.put("quantifies", 10 - classes.getQuantifies().size());
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", json);
    }

    /**
     * @return 给校长选择其要查看的班级
     */
    @GetMapping("/showClasses")
    public Object showClasses() {
        Session session = SecurityUtils.getSubject().getSession();
        if (session.getAttribute("principal") != null) {
            List<Class> classList = classJpa.findAll();
            List<ClassName> datas = new ArrayList<>();
            for (Class clazz : classList) {
                ClassName name = new ClassName();
                name.setClassCode(clazz.getClassroomCode());
                name.setClassName(clazz.getClassName());
                name.setClassID(clazz.getClassId());
                name.setClassPhoto(clazz.getClassBadge());
                datas.add(name);
            }
            return datas;
        }
        return null;
    }

    @PostMapping("/badge")
    public Object badge(String img, String className, String suffixName) {
        try {
            Class clazz = classJpa.findByClassName(className);
            clazz.setClassBadge(ImgUtils.base64ToImg(img, clazz.getClassroomCode() + "." + suffixName, ""));
            classJpa.save(clazz);
            return new JsonObjectResult(ResultCode.SUCCESS, "上传成功");
        } catch (IOException e) {
            return new JsonObjectResult(ResultCode.EXCEPTION, e.getMessage());
        }
    }

    @PostMapping("/showBadge")
    public Object showBadge(String className) {
        return new JsonObjectResult(ResultCode.SUCCESS, "", classJpa.getAllBadge(className));
    }

    /**
     * 定时任务随着服务器的启动启动
     * 切换课程
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void isEnd() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        for (String ip : IpList) {
            ChannelId channelId = (ChannelId) nettyMap.get(ip);
            if (channelId != null) {
                Channel channel = NettyChannelHandlerPool.channelGroup.find(channelId);
                Map<String, Object> map = new HashMap<>();
                List<Map<String, Object>> schedules = new ArrayList<>();
                Set<Schedule> queue = ((Set<Schedule>) IP_SCHEDULE.get(ip));
                map.put("type", 1);
                if (queue.size() > 0 && channel != null) {
                    int i = 0;
                    Schedule set = null;
                    for (Schedule schedule : queue) {
                        try {
                            if (schedule != null && i < 2) {
                                Map<String, Object> datas = new HashMap<>();
                                if (!schedule.getScheduleDate().equals(new SimpleDateFormat("EE").format(new Date(System.currentTimeMillis())))) {
                                    continue;
                                } else if (format.parse(schedule.getLessonEndtime()).getTime() < format.parse(format.format(new Time(System.currentTimeMillis()))).getTime()) {
                                    continue;
                                }
                                datas.put("courseName", schedule.getCourseName().getCourseName());
                                datas.put("startTime", schedule.getCourseStarttime().substring(0, schedule.getCourseStarttime().lastIndexOf(":")));
                                datas.put("endTime", schedule.getLessonEndtime().substring(0, schedule.getLessonEndtime().lastIndexOf(":")));
                                datas.put("teacherId", schedule.getInstructor().getTeacherId());
                                datas.put("teacherName", schedule.getInstructor().getTeacherName());
                                datas.put("headUrl", schedule.getInstructor().getHeadPhoto());
                                datas.put("lessonRepresentative", schedule.getLessonRepresentative());
                                schedules.add(datas);
                                if (i == 0) set = schedule;
                                i++;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (set != null) {
                        queue.remove(set);
                    }
                    if (schedules.isEmpty()) {
                        map.put("message", "今日课程已结束");
                        map.put("data", new ArrayList<>());
                    } else {
                        map.put("message", "处理数据成功");
                        map.put("data", schedules);
                    }
                    channel.writeAndFlush(Unpooled.copiedBuffer(new Gson().toJson(map), CharsetUtil.UTF_8));
                } else {
                    if (channel == null) {
                        System.out.println();
                        System.out.println("pad");
                        System.out.println();
                        logger.warn("未找到班牌终端");
                    }
                }
            }
        }
        if (ctxList.size() > 0 && CODE_CHANNELID.containsValue(ctxList.get(ctxList.size() - 1).channel().id())) {
            Iterator code = CODE_CHANNELID.keySet().iterator();
            while (code.hasNext()) {
                Object classCode = code.next();
                if (classCode != "" && classCode != null) {
                    ChannelId channelId = CODE_CHANNELID.get(classCode);
                    if (channelId != null) {
                        Channel channel = NettyChannelHandlerPool.channelGroup.find(channelId);
                        Map<String, Object> map = new HashMap<>();
                        List<Map<String, Object>> schedules = new ArrayList<>();
                        map.put("type", 1);
                        Set<Schedule> queue = classJpa.findByClassroomCode(Integer.parseInt(classCode.toString())).getSchedules();
                        if (queue.size() > 0 && channel != null) {
                            int i = 0;
                            Schedule set = null;
                            for (Schedule schedule : queue) {
                                try {
                                    if (schedule != null && i < 2) {
                                        Map<String, Object> datas = new HashMap<>();
                                        if (!schedule.getScheduleDate().equals(new SimpleDateFormat("EE").format(new Date(System.currentTimeMillis())))) {
                                            continue;
                                        } else if (format.parse(schedule.getLessonEndtime()).getTime() < format.parse(format.format(new Time(System.currentTimeMillis()))).getTime()) {
                                            continue;
                                        }
                                        datas.put("courseName", schedule.getCourseName().getCourseName());
                                        datas.put("startTime", schedule.getCourseStarttime().substring(0, schedule.getCourseStarttime().lastIndexOf(":")));
                                        datas.put("endTime", schedule.getLessonEndtime().substring(0, schedule.getLessonEndtime().lastIndexOf(":")));
                                        datas.put("teacherId", schedule.getInstructor().getTeacherId());
                                        datas.put("teacherName", schedule.getInstructor().getTeacherName());
                                        datas.put("headUrl", schedule.getInstructor().getHeadPhoto());
                                        datas.put("lessonRepresentative", schedule.getLessonRepresentative());
                                        schedules.add(datas);
                                        if (i == 0) set = schedule;
                                        i++;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (set != null) {
                                queue.remove(set);
                            }
                            if (schedules.isEmpty()) {
                                map.put("message", "今日课程已结束");
                                map.put("data", new ArrayList<>());
                            } else {
                                map.put("message", "处理数据成功");
                                map.put("data", schedules);
                            }
                            channel.writeAndFlush(Unpooled.copiedBuffer(new Gson().toJson(map), CharsetUtil.UTF_8));
                        } else {
                            if (channel == null) {
                                logger.warn("未找到班牌终端");
                            }
                        }
                    }
                }
            }
        }
    }

    @PostMapping("/startNetty")
    public void startNetty(String classCode) {
        CODE_CHANNELID.put(classCode, ctxList.get(ctxList.size() - 1).channel().id());
    }

    /**
     * 当终端连接时将当前连接的管道与对应的IP地址存储到map集合中
     * 并且连接时就将当前课程发送过去
     *
     * @param ctx 连接
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        super.channelActive(ctx);
        for (String ip : IpList) {
            if (!nettyMap.containsKey(ip)) {
                nettyMap.put(ip, ctx.channel().id());
            }
        }
        isEnd();
    }

    /**
     * @param ctx   连接
     * @param cause 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        super.exceptionCaught(ctx, cause);
        Channel channel = NettyChannelHandlerPool.channelGroup.find(ctx.channel().id());
        if (channel != null) {
            channel.close();
            String remoteAddress = ctx.channel().remoteAddress().toString();
            nettyMap.remove(remoteAddress.substring(1, remoteAddress.indexOf(":")));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        super.channelRead(ctx, msg);
    }

}
