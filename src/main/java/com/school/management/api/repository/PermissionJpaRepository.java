package com.school.management.api.repository;

import com.school.management.api.entity.Permission;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Lazy(value = false)
@Transactional
@Repository
public interface PermissionJpaRepository extends JpaRepository<Permission, Long> {

    /**
     * @param ID 角色ID
     * @return 根据角色ID来查询用户属于什么角色
     */
    Permission getByPerId(Long ID);

    Permission findByPerName(String name);

}
