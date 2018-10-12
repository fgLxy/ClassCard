package com.school.management.api.repository;

import com.school.management.api.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface AlbumJpaRepository extends JpaRepository<Album, Long> {

    /**
     * @param id 删除一个相册ID
     */
    @Modifying
    @Query(value = "DELETE FROM album WHERE album_id = ?1", nativeQuery = true)
    int deleteAlbumById(Long id);

    /**
     * @param id 相册ID
     * @return 根据相册ID查询的到的相册
     */
    Album getAlbumById(Long id);

    /**
     * @param classRoomCode 班级编号
     * @return 根据班级名称查询得到的相册集合
     */
    List<Album> getAllByClassRoomCodeAndAlbumType(int classRoomCode, int type);

    /**
     * @param start 开始的条目数
     * @param size  每页显示的数量
     * @return 分页后
     */
    @Query(value = "select * from album limit ?1, ?2", nativeQuery = true)
    List<Album> getByPage(int start, int size);

    List<Album> findByAddedDateLike(String date);

    Page<Album> findByAlbumType(int type, Pageable pageable);

    List<Album> findByAlbumType(int type);

    Page<Album> findByAlbumTypeAndClassRoomCode(int type, int classCode, Pageable pageable);

}
