package com.school.management.api.controller;

import com.google.gson.Gson;
import com.school.management.api.entity.MicroVideo;
import com.school.management.api.repository.MicroVideoJpaRepository;
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
@RequestMapping("/school/micro")
public class MicroVideoController {

    @Autowired
    private MicroVideoJpaRepository microJpa;

    @RequestMapping("/list")
    public Object list(@RequestParam(defaultValue = "1") int page) {
        return new JsonObjectResult(ResultCode.SUCCESS, "", microJpa.findAll(PageRequest.of(page - 1, 10)));
    }

    @RequestMapping("/upload")
    public Object upload(HttpServletRequest request) {
        try {
            List<String> URLs = ImgUtils.filesToImg((MultipartHttpServletRequest) request, "videos");
            MicroVideo microVideo = new MicroVideo();
            microVideo.setMicroVideoTitle(request.getParameter("title"));
            microVideo.setMicroVideoDate(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
            microVideo.setMicroVideoURL(URLs.get(1));
            microVideo.setMicroVideoCover(URLs.get(0));
            microJpa.save(microVideo);
            return new JsonObjectResult(ResultCode.SUCCESS, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonObjectResult(ResultCode.EXCEPTION, e.getMessage());
        }
    }

    @PostMapping("/directory")
    public Object directory() {
        return new JsonObjectResult(ResultCode.SUCCESS, "", microJpa.directory());
    }

    @PostMapping("/delete")
    public Object delete(String microIds) {
        try {
            String[] microIdes = microIds.split(",");
            for (String microId : microIdes) {
                microJpa.delete(microJpa.findById(Long.parseLong(microId)).get());
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new JsonObjectResult(ResultCode.EXCEPTION, e.getMessage());
        }
    }

    @PostMapping("/modify")
    public Object modify(MicroVideo video) {
        Optional<MicroVideo> optional = microJpa.findById(video.getMicroVideoId());
        if (optional.isPresent()) {
            microJpa.saveAndFlush(video);
            return new JsonObjectResult(ResultCode.SUCCESS, "修改成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "没有找到待修改的数据");
    }

    @PostMapping("/all")
    public Object all() {
        return new JsonObjectResult(ResultCode.SUCCESS, "", microJpa.findAll());
    }

    @PostMapping("/info")
    public Object info(String microID) {
        Optional<MicroVideo> optional = microJpa.findById(Long.parseLong(microID));
        return new JsonObjectResult(ResultCode.SUCCESS, "", optional.orElse(null));
    }
}
