package com.school.management.api.repository;

import com.school.management.api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface RoleJpaRepository extends JpaRepository<Role, Long> {

    /**
     * @param ID 权限ID
     * @return 根据权限ID来查询对应的权限
     */
    Role findByRoleId(Long ID);
}
