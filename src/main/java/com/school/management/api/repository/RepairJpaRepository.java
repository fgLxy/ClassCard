package com.school.management.api.repository;

import com.school.management.api.entity.Repair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairJpaRepository extends JpaRepository<Repair, Long> {

    /**
     * @param classCode  班级编号
     * @param repairDate 保修日期
     * @return 根据班级编号和报修日期来查找对应的报修信息
     */
    Repair findByClassCodeAndRepairDate(int classCode, String repairDate);

    Repair findByClassCodeAndRepairDateLike(int classCode, String repairDate);

    Repair findByRepairId(long repairId);

    List<Repair> findByClassCodeAndRepairStatus(int classCode, int status);

    List<Repair> findByRepairStatus(int status);

    Page<Repair> findByRepairStatusAndClassCode(int status, int classCode, Pageable pageable);

    List<Repair> findByRepairStatusAndClassCode(int status, int classCode);

    Page<Repair> findAllByClassCode(int classCode, Pageable pageable);
}
