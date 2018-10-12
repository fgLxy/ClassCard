package com.school.management.api.controller;

import com.google.gson.Gson;
import com.school.management.api.entity.User;
import com.school.management.api.repository.PermissionJpaRepository;
import com.school.management.api.repository.UserJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/school/user")
public class UserController {

    @Autowired
    private UserJpaRepository userJpa;

    @Autowired
    private PermissionJpaRepository perJpa;

    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "1") int page) {
//        List<Map<String, Object>> datas = new ArrayList<>();
//        if (users != null) {
//            for (User user : users) {
//                Map<String, Object> data = new HashMap<>();
//                data.put("userId", user.getUserId());
//                data.put("userName", user.getUserName());
//                data.put("password", user.getUserPassword());
//                if (user.getPermission() == null) {
//                    data.put("perName", user.getPermission());
//                } else {
//                    data.put("perName", user.getPermission().getPerName());
//                }
//                data.put("addTime", user.getAddDate());
//                datas.add(data);
//            }
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", userJpa.findAll(PageRequest.of(page - 1, 8)));
//        } else {
//            return new JsonObjectResult(ResultCode.EXCEPTION, "没有获取到数据");
//        }
    }

    @PostMapping("/delete")
    public Object delete(String id) {
        User user = userJpa.findByUserId(Long.parseLong(id));
        if (user != null) {
            user.setPermission(null);
            userJpa.delete(userJpa.saveAndFlush(user));
            return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "删除失败");
    }

    @PostMapping("/deleteSome")
    public Object deleteSome(String Ids) {
        if (Ids != null) {
            String[] repairIds = Ids.split(",");
            for (String repairId : repairIds) {
                userJpa.delete(userJpa.findByUserId(Long.parseLong(repairId)));
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "批量删除成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR);
    }

    @PostMapping("/add")
    public Object add(User user) {
        user.setPermission(perJpa.getByPerId(user.getPermission().getPerId()));
        return new JsonObjectResult(ResultCode.SUCCESS, "增加数据成功", userJpa.saveAndFlush(user));
    }

    @PostMapping("/update")
    public Object update(User user) {
        User old = userJpa.findByUserId(user.getUserId());
        if (old != null) {
            return new JsonObjectResult(ResultCode.SUCCESS, "修改数据成功", user);
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "修改数据失败，找不到待修改的数据");
    }

    @GetMapping("/toAdd")
    public Object toAdd() {
        return perJpa.findAll();
    }

    @PostMapping("/query")
    public Object query(String date) {
        System.out.println(date);
        return new JsonObjectResult(ResultCode.SUCCESS, "", userJpa.findByPermission(perJpa.findByPerName(date)));
    }
}
