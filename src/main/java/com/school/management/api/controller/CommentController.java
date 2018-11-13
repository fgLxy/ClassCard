package com.school.management.api.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.school.management.api.entity.Class;
import com.school.management.api.entity.Comment;
import com.school.management.api.entity.Student;
import com.school.management.api.entity.Teacher;
import com.school.management.api.repository.ClassJpaRepository;
import com.school.management.api.repository.CommentJpaRepository;
import com.school.management.api.repository.StudentJpaRepository;
import com.school.management.api.repository.TeacherJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.utils.ImgUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/school/comment")
public class CommentController {

    @Autowired
    private CommentJpaRepository commentJpa;

    @Autowired
    private TeacherJpaRepository teacherJpa;

    @Autowired
    private StudentJpaRepository studentJpa;

    @Autowired
    private ClassJpaRepository classJpa;

    @Autowired
    private ValueOperations<String, Object> operations;

    @PostMapping("/allParent")
    public Object all(String classCode) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Class clazz = classJpa.findByClassroomCode(Integer.parseInt(classCode));
        Teacher teacher = clazz.getClassHeadmaster();
        Set<Student> studentList = clazz.getStudents();
        List<Map<String, Object>> datas = new ArrayList<>();
        for (Student student : studentList) {
            Map<String, Object> map = commentJpa.findAllBy((int) teacher.getTeacherId(), student.getStudentNum());
            if (map != null && map.size() != 0) {
                Map<String, Object> data = new HashMap<>(map);
                Date date = sdf.parse(data.get("comment_time").toString());
                if (date.getDate() < new Date(System.currentTimeMillis()).getDate()) {
                    data.put("comment_time", "昨天");
                } else {
                    data.put("comment_time", new SimpleDateFormat("HH:mm:ss").format(date));
                }
                datas.add(data);
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("comment_parent", student.getStudentNum());
                data.put("student_name", student.getStudentName());
                data.put("teacher_name", teacher.getTeacherName());
                data.put("student_classroom", student.getStudentClassroom());
                data.put("comment_teacher", teacher.getTeacherId());
                data.put("comment_id", 0);
                data.put("comment_time", "");
                data.put("comment_status", 0);
                data.put("comment_account", "您已经与该学生/老师建立了连接");
                data.put("comment_type", 0);
                data.put("comment_photo_1", "");
                data.put("comment_photo_2", "");
                data.put("comment_photo_3", "");
                data.put("student_num", student.getStudentNum());
                data.put("teacher_ID", teacher.getTeacherId());
                datas.add(data);
            }
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "", datas);
    }

    @PostMapping("/asParent")
    public Object asParent(String classCode, String studentName) {
        Class clazz = classJpa.findByClassroomCode(Integer.parseInt(classCode));
        String className = clazz.getClassName();
        Teacher headmaster = clazz.getClassHeadmaster();
        Student student = studentJpa.findByStudentNameAndStudentClassroom(studentName, className);
        List<Map<String, Object>> mapList = commentJpa.findAllByStudentId(student.getStudentNum());
        List<Map<String, Object>> datas = new ArrayList<>();
        if (!mapList.isEmpty()) {
            for (Map<String, Object> data : mapList) {
                Map<String, Object> old = new HashMap<>(data);
                Object teacher = data.get("comment_teacher");
                if (teacher == null) {
                    data.replace("comment_teacher", headmaster.getTeacherId());
                }
                old.replace("comment_status", 1);
                datas.add(old);
            }
        }
        for (Comment comment : commentJpa.findByCommentParentAndCommentStatus(student.getStudentNum(), 0)) {
            comment.setCommentStatus(1);
            commentJpa.saveAndFlush(comment);
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "", datas);
    }

    @PostMapping("/add")
    public Object addComment(String parentId, String teacherId, String content, HttpServletRequest request) {
        if (StringUtils.isEmpty(teacherId) || StringUtils.isEmpty(parentId)) {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "教师ID和家长不能为空");
        }
        Teacher teacher = teacherJpa.findByTeacherId(Integer.parseInt(teacherId));
        Student student = studentJpa.findByStudentNum(Integer.parseInt(parentId));
        MultipartHttpServletRequest multipartHttpServletRequest = ((MultipartHttpServletRequest) request);
        List<String> urls;
        try {
            urls = ImgUtils.filesToImg(multipartHttpServletRequest, "images\\comment");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonObjectResult(ResultCode.EXCEPTION, e.getMessage());
        }
        Session session = SecurityUtils.getSubject().getSession();
        if (teacher != null && student != null) {
            Comment comment = new Comment();
            comment.setCommentAccount(content);
            comment.setCommentParent(Integer.parseInt(parentId));
            comment.setCommentStatus(0);
            comment.setCommentTeacher(Integer.parseInt(teacherId));
            comment.setTeacher(teacher);
            comment.setCommentTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
            if (!urls.isEmpty()) {
                switch (urls.size()) {
                    case 1:
                        comment.setCommentPhoto1(urls.get(0));
                        break;
                    case 2:
                        comment.setCommentPhoto1(urls.get(0));
                        comment.setCommentPhoto2(urls.get(1));
                        break;
                    case 3:
                        comment.setCommentPhoto1(urls.get(0));
                        comment.setCommentPhoto2(urls.get(1));
                        comment.setCommentPhoto3(urls.get(2));
                        break;
                    default:
                        break;
                }
            }
            if (session.getAttribute("teacher") != null) {
//                教师登录
                comment.setCommentType(1);
            } else {
//                家长登录
                comment.setCommentType(0);
            }
//            return new JsonObjectResult(ResultCode.SUCCESS, "");
            return new JsonObjectResult(ResultCode.SUCCESS, "添加成功", commentJpa.saveAndFlush(comment));
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "参数错误");
    }

    @PostMapping("/addWorkOrNotice")
    public Object addWorkOrNotice(String classCode, String content, HttpServletRequest request) {
        Gson gson = new Gson();
        Class clazz = gson.fromJson(operations.get(classCode).toString(), new TypeToken<Class>() {
        }.getType());
        Set<Student> studentSet = clazz.getStudents();
        Teacher teacher = clazz.getClassHeadmaster();
        List<Comment> comments = new ArrayList<>(studentSet.size());
        try {
            List<String> urls = null;
            if (request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                urls = ImgUtils.filesToImg(multipartHttpServletRequest, "images\\comment");
            }

            for (Student student : studentSet) {
                Comment comment = new Comment();
                comment.setCommentType(0);
                comment.setCommentStatus(0);
                comment.setCommentTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
                comment.setCommentParent(student.getStudentNum());
                comment.setTeacher(teacher);
                comment.setCommentAccount(content);
                if (urls != null && !urls.isEmpty()) {
                    switch (urls.size()) {
                        case 1:
                            comment.setCommentPhoto1(urls.get(0));
                            break;
                        case 2:
                            comment.setCommentPhoto1(urls.get(0));
                            comment.setCommentPhoto2(urls.get(1));
                            break;
                        case 3:
                            comment.setCommentPhoto1(urls.get(0));
                            comment.setCommentPhoto2(urls.get(1));
                            comment.setCommentPhoto3(urls.get(2));
                            break;
                        default:
                            break;
                    }
                }
                comments.add(comment);
            }
        } catch (Exception e) {
            return new JsonObjectResult(ResultCode.EXCEPTION, e.getMessage());
        }
        System.out.println(gson.toJson(comments));
        commentJpa.saveAll(comments);
        return new JsonObjectResult(ResultCode.SUCCESS, "添加作业/通知成功，已推送至个家长客户端");
    }

//    @PostMapping("/upload")
//    public Object uploadImage(String image, HttpServletRequest request) {
//        try {
//            ImgUtils.androidUploadImage(image, request);
//        } catch (Exception e) {
//            return new JsonObjectResult(ResultCode.UNKNOWN_ERROR, "接收图片失败", false);
//        }
//        return new JsonObjectResult(ResultCode.SUCCESS, "接收图片成功", true);
//    }
//
//    @PostMapping(value = "/uploadMore")
//    public Object handleFileUpload(HttpServletRequest request) {
//        String filePath = "D:\\aim\\";
//
//
//        MultipartHttpServletRequest multipartHttpServletRequest = ((MultipartHttpServletRequest) request);
//        Iterator<String> it = multipartHttpServletRequest.getFileNames();
//        File createFile = new File(filePath);
//        if (!createFile.exists()) {
//            createFile.mkdir();
//        }
//        while (it.hasNext()) {
//            String fileName = it.next();
//
//            MultipartFile file = multipartHttpServletRequest.getFile(fileName);
//            BufferedOutputStream stream = null;
//            if (!file.isEmpty()) {
//                try {
//
//                    byte[] bytes = file.getBytes();
//                    stream = new BufferedOutputStream(new FileOutputStream(new File(createFile, file.getOriginalFilename())));//设置文件路径及名字
//                    stream.write(bytes);// 写入
//                    stream.close();
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                    stream = null;
//                    return new JsonObjectResult(ResultCode.EXCEPTION, "名为 " + fileName + " 的文件上传失败  ==> " + e.getMessage());
//                }
//            } else {
//                return new JsonObjectResult(ResultCode.EXCEPTION, "名为 " + fileName + " 的文件上传失败，因为文件为空");
//            }
//        }
//        return new JsonObjectResult(ResultCode.SUCCESS, "上传成功");
//    }
}
