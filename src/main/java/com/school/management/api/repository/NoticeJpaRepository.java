package com.school.management.api.repository;

import com.school.management.api.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface NoticeJpaRepository extends JpaRepository<Notice, Long> {

    /**
     * @param noticeID 通知ID
     * @return 根据通知ID来查找对应的通知信息
     */
    Notice findByNoticeId(long noticeID);

    /**
     * @param time 日期
     * @return 根据日期查询得到的部分通知
     */
    List<Notice> findByNoticeTimeLike(String time);

    Page<Notice> findByNoticeTimeLike(String time, Pageable pageable);
}
