package com.school.management.api.controller;

import com.school.management.api.entity.Video;
import com.school.management.api.repository.VideoJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/school/video")
public class VideoController {

    @Autowired
    VideoJpaRepository videoJpaRepository;

    /**
     * 获取所有的数据
     */
    @GetMapping(value = "/getAllVideo")
    public Object getAllVideo() {
        List<Video> list = videoJpaRepository.findAll();

        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", list);
    }

    /**
     * 获取一条数据
     */
    @PostMapping(value = "/getVideoUrl")
    public Object getVideoUrl(@RequestParam("class_code") String classCode) {
        Video videoInfo = videoJpaRepository.findAllByClassCode(classCode);

        if (!"".equals(classCode)) {
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", videoInfo);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "参数错误，获取数据成功", videoInfo);
        }
    }

    /**
     * 添加一条数据
     */
    @PostMapping(value = "/addVideoInfo")
    public Object addVideoInfo(@RequestParam("stream_url") String streamUrl,
                               @RequestParam("class_code") String classCode,
                               @RequestParam("class_name") String className,
                               @RequestParam("camera_code") String cameraCode) {
        Video video = new Video();

        video.setStreamUrl(streamUrl);
        video.setClassCode(classCode);
        video.setClassName(className);
        video.setCameraCode(cameraCode);
        videoJpaRepository.save(video);

        if (!"".equals(streamUrl) && !"".equals(classCode) && !"".equals(className) && !"".equals(cameraCode)) {
            return new JsonObjectResult(ResultCode.SUCCESS, "添加数据成功", video);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "添加数据失败", video);
        }
    }

    /**
     * 编辑视频流信息
     */
    @PostMapping(value = "/editVideoInfo")
    public Object editVideoInfo(@RequestParam("id") Long id,
                                @RequestParam("stream_url") String streamUrl,
                                @RequestParam("class_code") String classCode,
                                @RequestParam("class_name") String className,
                                @RequestParam("camera_code") String cameraCode) {
        int editVideo = videoJpaRepository.updateVideoInfo(id, streamUrl, classCode, className, cameraCode);

        if (id != 0 && !"".equals(streamUrl) && !"".equals(classCode) && !"".equals(className) && !"".equals(cameraCode)) {
            return new JsonObjectResult(ResultCode.SUCCESS, "修改数据成功", editVideo);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "修改数据失败", editVideo);
        }
    }

}
