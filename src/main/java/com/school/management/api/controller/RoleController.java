package com.school.management.api.controller;

import com.school.management.api.repository.RoleJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/school/role")
public class RoleController {

    @Autowired
    private RoleJpaRepository jpa;

    /**
     * @return 显示所有的权限
     */
    @RequestMapping("/listAll")
    public Object showAll() {
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", jpa.findAll());
    }

}
