package com.school.management.api.repository;

import com.school.management.api.entity.MicroVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface MicroVideoJpaRepository extends JpaRepository<MicroVideo, Long> {

    @Query(nativeQuery = true, value = "select micro_video_id, micro_video_title, micro_video_cover from micro_video")
    List<Map<String, Object>> directory();
}
