package com.school.management.api.controller;

import com.school.management.api.entity.ShortVideo;
import com.school.management.api.repository.ShortVideoJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/school/short")
public class ShortVideoController {

    @Autowired
    private ShortVideoJpaRepository shortJpa;

    @RequestMapping("/list")
    public Object list(@RequestParam(defaultValue = "1") int page) {
        return new JsonObjectResult(ResultCode.SUCCESS, "", shortJpa.findAll(PageRequest.of(page - 1, 10)));
    }

    @RequestMapping("/upload")
    public Object upload(HttpServletRequest request) {
        try {
            List<String> URLs = ImgUtils.filesToImg((MultipartHttpServletRequest) request, "Short videos");
            ShortVideo shortVideo = new ShortVideo();
            shortVideo.setShortVideoTitle(request.getParameter("title"));
            shortVideo.setShortVideoDate(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
            shortVideo.setShortVideoURL(URLs.get(1));
            shortVideo.setShortVideoCover(URLs.get(0));
            shortJpa.save(shortVideo);
            return new JsonObjectResult(ResultCode.SUCCESS, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonObjectResult(ResultCode.EXCEPTION, e.getMessage());
        }
    }

    @PostMapping("/directory")
    public Object directory() {
        return new JsonObjectResult(ResultCode.SUCCESS, "", shortJpa.directory());
    }

    @PostMapping("/delete")
    public Object delete(String shortIds) {
        try {
            String[] shortIdes = shortIds.split(",");
            for (String shortId : shortIdes) {
                shortJpa.delete(shortJpa.findById(Long.parseLong(shortId)).get());
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new JsonObjectResult(ResultCode.EXCEPTION, e.getMessage());
        }
    }

    @PostMapping("/modify")
    public Object modify(ShortVideo video) {
        Optional<ShortVideo> optional = shortJpa.findById(video.getShortVideoId());
        if (optional.isPresent()) {
            shortJpa.saveAndFlush(video);
            return new JsonObjectResult(ResultCode.SUCCESS, "修改成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "没有找到待修改的数据");
    }

    @PostMapping("/all")
    public Object all() {
        return new JsonObjectResult(ResultCode.SUCCESS, "", shortJpa.findAll());
    }

    @PostMapping("/info")
    public Object info(String shortID) {
        Optional<ShortVideo> optional = shortJpa.findById(Long.parseLong(shortID));
        return new JsonObjectResult(ResultCode.SUCCESS, "", optional.orElse(null));
    }
}
