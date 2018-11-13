package com.school.management.api.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.school.management.api.entity.HomeWork;
import com.school.management.api.repository.ClassJpaRepository;
import com.school.management.api.repository.HomeWorkJpaRepository;
import com.school.management.api.repository.StudentJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/school/homework")
public class HomeWorkController {

    @Autowired
    private HomeWorkJpaRepository workJpa;

    @Autowired
    private StudentJpaRepository studentJpa;

    @Autowired
    private ClassJpaRepository classJpa;

    @PostMapping("/all")
    public Object all(String classCode) {
        Gson gson = new Gson();
        String json = gson.toJson(workJpa.findByClassCode(Integer.parseInt(classCode)));
        List<Map<String, Object>> datas = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
        for (Map<String, Object> data : datas) {
            data.put("workPhotos", gson.fromJson(data.get("workPhotos").toString(), new TypeToken<List<String>>() {
            }.getType()));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "", datas);
    }

    @PostMapping("/add")
    public Object add(HomeWork homeWork, HttpServletRequest request) {
        try {
            homeWork.setWorkDate(new Date(System.currentTimeMillis()));
            homeWork.setWorkPhotos(new Gson().toJson(ImgUtils.filesToImg((MultipartHttpServletRequest) request, "images\\homeWork")));
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonObjectResult(ResultCode.EXCEPTION, e.getMessage());
        }
//        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功");
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", workJpa.saveAndFlush(homeWork));
    }
}
