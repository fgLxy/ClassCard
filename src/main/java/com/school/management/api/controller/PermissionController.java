package com.school.management.api.controller;

import com.school.management.api.entity.Permission;
import com.school.management.api.entity.Role;
import com.school.management.api.entity.User;
import com.school.management.api.repository.PermissionJpaRepository;
import com.school.management.api.repository.UserJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/school/permisson")
public class PermissionController {

    @Autowired
    private UserJpaRepository userJpa;

    @Autowired
    private PermissionJpaRepository perJpa;

    /**
     * @param request see {@link HttpServletRequest}
     * @return 展示当前用户的角色以及其拥有的权限内容
     */
    @GetMapping("/showUserPer")
    public Object showUserPer(HttpServletRequest request) {
        Map<Object, Object> map = new HashMap<>();
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Permission permission = user.getPermission();
            List<Role> roles = permission.getRoles();
            map.put("permissionID", permission.getPerId());
            map.put("permissionName", permission.getPerName());
            map.put("roles", roles);
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", map);
        } else {
            return new JsonObjectResult(ResultCode.INVALID_AUTHCODE, "登录信息失效");
        }
    }
}
