package com.school.management.api.repository;


import com.school.management.api.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsJpaRepository extends JpaRepository<News, Long> {

    /**
     * @return 返回所有的新闻信息集合
     */
    List<News> findAllBy();

    /**
     * @param newsId 新闻ID
     * @return 根据新闻ID来查询对应的新闻
     */
    News getNewsByNewsId(Long newsId);

    @Query(value = "select * from news limit ?1, ?2", nativeQuery = true)
    List<News> findByPage(int start, int page);

    Page<News> findByPublishDateLike(String date, Pageable pageable);
}
