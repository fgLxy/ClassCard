package com.school.management.api.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.school.management.api.entity.Class;
import com.school.management.api.entity.Quantify;
import com.school.management.api.repository.ClassJpaRepository;
import com.school.management.api.repository.QuantifyJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.utils.ImgUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/school/quantify")
@RestController
public class QuantifyController {

    @Autowired
    private QuantifyJpaRepository jpa;

    @Autowired
    private ClassJpaRepository classJpa;

    @PostMapping("/query")
    public Object query(String className) {
        return list(1, className);
    }

    @RequestMapping("/list")
    public Object list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "0") String classId) {
        List<Quantify> quantifies = null;
        if (classId == null) {
            quantifies = jpa.findAll(new PageRequest(page - 1, 8)).getContent();
        } else {
            quantifies = jpa.findByClassCode(classJpa.findByClassName(classId).getClassroomCode(), new PageRequest(page - 1, 8)).getContent();
        }
        List<Map<String, Object>> datas = new ArrayList<>();
        for (Quantify quantify : quantifies) {
            Map<String, Object> data = new HashMap<>();
            Class aClass = classJpa.findByClassId(quantify.getClassId());
            data.put("quantifyId", quantify.getQuantifyId());
            data.put("className", aClass.getClassName());
            data.put("classHeadmaster", aClass.getClassHeadmaster().getTeacherName());
            data.put("quantifyType", quantify.getQuantifyType());
            if (aClass.getClassMonitor() != null) {
                data.put("classMonitor", aClass.getClassMonitor().getStudentName());
            } else {
                data.put("classMonitor", null);
            }
            data.put("quantifyRemark", quantify.getQuantifyRemark());
            datas.add(data);
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "", datas);
    }

    @RequestMapping("/add")
    public Object add(Quantify quantify, HttpServletRequest request) {
        if (quantify != null) {
            try {
                List<String> urls = ImgUtils.filesToImg((MultipartHttpServletRequest) request, "images\\quantify");
                quantify.setQuantifyDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
                quantify.setQuantifyPhotoUrl(new Gson().toJson(urls));
            } catch (Exception e) {
                e.printStackTrace();
                return new JsonObjectResult(ResultCode.EXCEPTION, e.getMessage());
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "增加成功", jpa.saveAndFlush(quantify));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "增加失败，信息为空");
    }

    @RequestMapping("/update")
    public Object update(Quantify quantify) {
        Quantify old = jpa.findByQuantifyId(quantify.getQuantifyId());
        if (old != null) {
            return new JsonObjectResult(ResultCode.SUCCESS, "修改成功", jpa.saveAndFlush(quantify));
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "修改失败");

    }

    @PostMapping("/delete")
    public Object delete(String id) {
        Quantify quantify = jpa.findByQuantifyId(Long.parseLong(id));
        if (quantify != null) {
            jpa.delete(quantify);
            return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "删除失败");
    }

    @PostMapping("/deleteSome")
    public Object deleteSome(String Ids) {
        String[] repairIds = Ids.split(",");
        for (String repairId : repairIds) {
            jpa.delete(jpa.findByQuantifyId(Long.parseLong(repairId)));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "批量删除成功");
    }

    @PostMapping("/all")
    public Object all(int classID) {
        return new JsonObjectResult(ResultCode.SUCCESS, "", jpa.findByClassCode(classID, new Sort(Sort.Direction.DESC, "quantifyType")));
    }

}
