package com.school.management.api.repository;

import com.school.management.api.entity.Permission;
import com.school.management.api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    /**
     * @param name 用户名
     * @return 根据用户名来查找用户信息
     */
    User getByUserName(String name);

    /**
     * @param email 邮箱
     * @return 根据邮箱来登录
     */
    User findByUserEmail(String email);

    /**
     * @param phone 手机号
     * @return 根据手机号来登录
     */
    User findByUserMobilephoneNum(String phone);

    User findByUserId(long userId);

    List<User> findByPermission(Permission permission);

    Page<User> findByPermission(Permission permission, Pageable pageable);
}
