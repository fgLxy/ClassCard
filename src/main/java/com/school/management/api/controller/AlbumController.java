package com.school.management.api.controller;

import com.google.gson.Gson;
import com.school.management.api.entity.Album;
import com.school.management.api.repository.AlbumJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/school/albums")
public class AlbumController {


    @Autowired
    AlbumJpaRepository albumJpaRepository;

    /**
     * 添加一个相册信息
     */
    @PostMapping(value = "/addAlbum")
    public Object addAlbum(Album album, HttpServletRequest request) {
        try {
            List<String> urls = ImgUtils.filesToImg((MultipartHttpServletRequest) request);
            for (String url : urls) {
                Album album1 = new Album();
                album1.setAlbumType(album.getAlbumType());
                album1.setClassRoomCode(album.getClassRoomCode());
                album1.setDescribe(album.getDescribe());
                album1.setPhotoUrl(url);
                album1.setAddedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
                albumJpaRepository.save(album1);
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "添加数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "添加数据失败：" + e.getMessage());
        }
    }

    /**
     * @return 图片是否已经保存好
     */
    @PutMapping("/uploadPhoto")
    public Object uploadPhoto(String[] img, String[] fileName) {
        try {
            ImgUtils.base64esToImg(img, fileName);
            return new JsonObjectResult(ResultCode.SUCCESS, "上传成功", ImgUtils.imgToBase64(fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonObjectResult(ResultCode.SUCCESS, "转换图片异常, 请稍后重试");
        }
    }

    /**
     * 删除一个相册
     * Delete请求
     * http://localhost:8080/school/albums/deleteAlbum?id=8
     */
    @DeleteMapping(value = "/deleteAlbum")
    public Object deleteOneById(@RequestParam("id") Long id) {
        albumJpaRepository.deleteAlbumById(id);
        if (id != 0) {
            return new JsonObjectResult(ResultCode.SUCCESS, "删除数据成功", albumJpaRepository.deleteAlbumById(id));
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "删除数据失败", albumJpaRepository.deleteAlbumById(id));
        }
    }

    /**
     * 获取一个相册JSON数据
     * Post请求
     * http://localhost:8080/school/albums/getAlbum?id=1
     */
    @PostMapping(value = "/getAlbum")
    public Object getAlbum(@RequestParam("id") Long id) {
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", albumJpaRepository.getAlbumById(id));
    }

    /**
     * 获取所有班级的相册JSON数据
     */
    @GetMapping(value = "/list")
    public Object getAlbums(@RequestParam(defaultValue = "1") int page, int type) {
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", albumJpaRepository.findByAlbumType(type, PageRequest.of(page - 1, 8)));
    }

    /**
     * 更新相册信息
     * Put请求
     */
    @PostMapping(value = "/updateAlbum")
    public Object upDateAlbum(Album album) {
        System.out.println(new Gson().toJson(album));
        Album old = albumJpaRepository.getAlbumById(album.getId());
        if (old != null) {
            try {
                return new JsonObjectResult(ResultCode.SUCCESS, "更新数据成功", albumJpaRepository.saveAndFlush(album));
            } catch (Exception e) {
                return new JsonObjectResult(ResultCode.PARAMS_ERROR, "更新数据失败" + e.getMessage());
            }
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "更新数据失败");
    }

    /**
     * 根据班级编号来获取本班级的相册JSON数据
     * Post请求
     * http://localhost:8080/school/albums/classAlbum?classCode=1
     */
    @PostMapping(value = "/classAlbum")
    public Object getAlbumByClassName(int classCode, int type) {
        List<Album> list = albumJpaRepository.getAllByClassRoomCodeAndAlbumType(classCode, type);
        return new JsonObjectResult(ResultCode.SUCCESS, "获取相册成功", list);
    }

    @PostMapping("/deleteSome")
    public Object deleteSome(String Ids) {
        System.out.println(Ids);
        try {
            String[] repairIds = Ids.split(",");
            for (String repairId : repairIds) {
                albumJpaRepository.delete(albumJpaRepository.getAlbumById(Long.parseLong(repairId)));
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "批量删除成功");
        } catch (Exception e) {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "批量删除失败" + e.getMessage());
        }
    }

    @PostMapping("/query")
    public Object query(String date) {
        System.out.println(date);
        return new JsonObjectResult(ResultCode.SUCCESS, "", albumJpaRepository.findByAddedDateLike(date + "%"));
    }
}
