package com.school.management.api.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.school.management.api.entity.News;
import com.school.management.api.repository.NewsJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.utils.ImgUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = "/school/news")
public class NewsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NewsJpaRepository newsJpaRepository;

    /**
     * @return 获取最新的一条新闻
     */
    @PostMapping("/showNew")
    public Object getLastNews() {
        List list = newsJpaRepository.findAll();
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", list.get(list.size() - 1));
    }

    /**
     * 获取所有新闻JSON数据
     */
    @PostMapping("/allNews")
    public Object getAllNews(@RequestParam(defaultValue = "1") int page) {
        List<News> news = newsJpaRepository.findAll(PageRequest.of(page - 1, 8)).getContent();
        List<Map<String, Object>> list = new ArrayList<>();
        for (News aNew : news) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", String.valueOf(aNew.getNewsId()));
            map.put("title", aNew.getTitle());
            list.add(map);
        }
        return list;
    }

    /**
     * 获取一个新闻信息JSON数据
     * Post请求
     * http://localhost:8080/school/albums/getAlbum?id=1
     */
    @PostMapping("/getNewsDetail")
    public Object getNewsDetail(String newsId) {
        if (Long.parseLong(newsId) != 0) {
            return newsJpaRepository.getNewsByNewsId(Long.parseLong(newsId));
        } else {
            return "获取数据失败";
        }
    }

    @PostMapping("/list")
    public Object list(@RequestParam(defaultValue = "") String date, @RequestParam(defaultValue = "1") int page) {
        if (date != null && !date.equals("") && !date.equals("undefind")) {
            return new JsonObjectResult(ResultCode.SUCCESS, "", newsJpaRepository.findByPublishDateLike(date + "%", PageRequest.of(page - 1, 8)));
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据成功", newsJpaRepository.findAll(PageRequest.of(page - 1, 8)));
    }

    @PostMapping("/add")
    public Object add(News news, String imageUrl) {
        System.out.println(imageUrl);
        List<Map<String, Object>> mapList = new Gson().fromJson(imageUrl, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
        try {
            for (Map<String, Object> map : mapList) {
                news.setImageUrl_1(ImgUtils.base64ToImg(map.get("base64").toString().split(",")[0], map.get("fileName").toString()));
            }

            return new JsonObjectResult(ResultCode.SUCCESS, "添加成功");//, newsJpaRepository.saveAndFlush(news));
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonObjectResult(ResultCode.EXCEPTION);
        }
    }

    @PostMapping("/delete")
    public Object delete(String newsId) {
        if (newsId != null && newsId != "undefind") {
            newsJpaRepository.delete(newsJpaRepository.getNewsByNewsId(Long.parseLong(newsId)));
            return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "删除失败");
    }

    @PostMapping("/deleteSome")
    public Object deleteSome(String newsIds) {
        String[] newsIDs = newsIds.split(",");
        for (String newsId : newsIDs) {
            newsJpaRepository.delete(newsJpaRepository.getNewsByNewsId(Long.parseLong(newsId)));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "批量删除成功");
    }

    @PostMapping("/update")
    public Object update(News news) {
        News old = newsJpaRepository.getNewsByNewsId(news.getNewsId());
        if (old != null) {
            return new JsonObjectResult(ResultCode.SUCCESS, "修改成功", newsJpaRepository.saveAndFlush(news));
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "待修改的数据已经过时。");
    }

    @PostMapping("/query")
    public Object query(String date, @RequestParam(defaultValue = "1") int page) {
        return new JsonObjectResult(ResultCode.SUCCESS, "", newsJpaRepository.findByPublishDateLike(date + "%", PageRequest.of(page - 1, 8)));
    }

    @PostMapping("/")
    public Object $y(String img, String fileName) {
        try {
            return ImgUtils.base64ToImg(img, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @PostMapping("/all")
    public Object all() {
        return new JsonObjectResult(ResultCode.SUCCESS, "", newsJpaRepository.findAll(new Sort(Sort.Direction.DESC, "newsId")));
    }
}
