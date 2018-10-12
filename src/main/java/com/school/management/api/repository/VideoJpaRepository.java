package com.school.management.api.repository;

import com.school.management.api.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface VideoJpaRepository extends JpaRepository<Video, Long> {

    /**
     * 获取一条视频流数据
     * */
    Video findAllByClassCode(@RequestParam("class_code") String classCode);

    /**
     * 获取所有的数据
     * */
    List<Video> findAll();

    /**
     * 修改一条数据
     * */
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE video SET stream_url = ?2,class_code=?3,class_name=?4,camera_code=?5 WHERE id = ?1",nativeQuery = true)
    int updateVideoInfo(@RequestParam("id") Long id,
                        @RequestParam("stream_url") String streamUrl,
                        @RequestParam("class_code") String classCode,
                        @RequestParam("class_name") String className,
                        @RequestParam("camera_code") String cameraCode);

    /**
     * 查询多条数据
     * */
    List<Video> queryAllByCameraCode(@RequestParam("class_code") String classCode);
}
