package com.school.management.api.repository;

import com.school.management.api.entity.Ip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IpJpaRepository extends JpaRepository<Ip, Long> {

    /**
     * @param IP 班牌IP
     * @return 根据班牌ID来查询对应的班级
     */
    Ip findByIp(String IP);

}
