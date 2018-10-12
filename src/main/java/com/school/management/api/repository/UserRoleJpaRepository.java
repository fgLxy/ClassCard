package com.school.management.api.repository;

import com.school.management.api.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


/**
 * 用户权限中间表查询，留作备用
 */
@Transactional
@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRole, Long> {
}
