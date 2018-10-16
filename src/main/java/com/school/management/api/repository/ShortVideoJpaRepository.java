package com.school.management.api.repository;

import com.school.management.api.entity.ShortVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ShortVideoJpaRepository extends JpaRepository<ShortVideo, Long> {

    @Query(nativeQuery = true, value = "select short_video_id, short_video_title, short_video_cover from short_video")
    List<Map<String, Object>> directory();
}
