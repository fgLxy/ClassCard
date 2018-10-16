package com.school.management.api.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.school.management.api.entity.Notice;
import com.school.management.api.netty.NettyServerHandler;
import com.school.management.api.repository.ClassJpaRepository;
import com.school.management.api.repository.IpJpaRepository;
import com.school.management.api.repository.NoticeJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.utils.GsonUtil;
import com.school.management.api.utils.ImgUtils;
import com.school.management.api.utils.NettyClientUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/school/notice")
public class NoticeController extends NettyServerHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NoticeJpaRepository noticeJpa;

    /**
     * @return 所有通知
     */
    @RequestMapping("/list")
    public Object noticeList(@RequestParam(defaultValue = "1") int page) {
        Page<Notice> list = noticeJpa.findAll(PageRequest.of(page - 1, 8));
        if (list.getContent().size() != 0) {
            List<Map<String, Object>> datas = new ArrayList<>();
            for (Notice notice : list) {
                Map<String, Object> data = new Gson().fromJson(new Gson().toJson(notice), new TypeToken<Map<String, Object>>() {
                }.getType());
                if (data.get("noticePhoto") != null) {
                    data.replace("noticePhoto", new Gson().fromJson(data.get("noticePhoto").toString(), new TypeToken<List<String>>() {
                    }.getType()));
                }
                data.put("totalPages", list.getTotalPages());
                datas.add(data);
            }
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据成功", datas);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败");
        }
    }

    /**
     * @param noticeID 通知ID
     * @return {@link Notice}
     */
    @PostMapping("/info")
    public Object oneNotice(long noticeID) {
        Notice notice = noticeJpa.findByNoticeId(noticeID);
        if (notice != null) {
            Map<String, Object> data = new Gson().fromJson(new Gson().toJson(notice), new TypeToken<Map<String, Object>>() {
            }.getType());
            List<String> photos = new Gson().fromJson(data.get("noticePhoto").toString(), new TypeToken<List<String>>() {
            }.getType());
            data.replace("noticePhoto", photos);
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", notice);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败");
        }
    }

    /**
     * @param notice 待更新的通知
     * @return 更新后的通知
     */
    @PostMapping("/update")
    public Object updateNotice(Notice notice) {
        Notice old = noticeJpa.findByNoticeId(notice.getNoticeId());
        if (old != null) {
            old.setNoticeContent(notice.getNoticeContent());
            old.setNoticeLevel(notice.getNoticeLevel());
            old.setNoticeSource(notice.getNoticeSource());
            old.setNoticeTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
            if (ctx != null) {
                this.channelRead(ctx, notice);
            }
            return new JsonObjectResult(ResultCode.SUCCESS, "更新数据成功", noticeJpa.saveAndFlush(old));
        } else {
            return new JsonObjectResult(ResultCode.UNKNOWN_ERROR);
        }
    }

    /**
     * @param notice 待添加的通知
     * @return 添加成功后的通知
     */
    @PostMapping("/add")
    public Object addNotice(Notice notice, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        notice.setNoticeTime(sdf.format(new Timestamp(System.currentTimeMillis())));
        if (request instanceof MultipartHttpServletRequest) {
            try {
                String json = new Gson().toJson(ImgUtils.filesToImg((MultipartHttpServletRequest) request, "images\\notice"));
                notice.setNoticePhoto(json);
            } catch (Exception e) {
                return new JsonObjectResult(ResultCode.EXCEPTION, e.getMessage());
            }
        }
        try {
            Map<String, Object> datas = new HashMap<>();
            Map<String, Object> map = new HashMap<>();
            datas.put("noticeId", notice.getNoticeId());
            datas.put("noticeTime", sdf.format(new Timestamp(System.currentTimeMillis())));
            datas.put("noticeLevel", notice.getNoticeLevel());
            datas.put("noticeContent", notice.getNoticeContent());
            datas.put("noticeSource", notice.getNoticeSource());
            map.put("type", 3);
            map.put("data", datas);
            map.put("message", "处理数据成功");
            this.sendAll(this.ctx, new Gson().toJson(map));
            return new JsonObjectResult(ResultCode.SUCCESS, "推送通知成功", noticeJpa.save(notice));
        } catch (Exception e) {
            logger.warn("添加通知异常", e);
            return new JsonObjectResult(ResultCode.SUCCESS, "推送通知异常，请检查是否有班牌开启状态");
        }
    }

    /**
     * @param noticeID 通知ID
     * @return true / false
     */
    @PostMapping("/delete")
    public Object removeNotice(long noticeID) {
        Notice notice = noticeJpa.findByNoticeId(noticeID);
        if (notice == null) {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "删除数据失败", false);
        } else {
            noticeJpa.delete(notice);
            return new JsonObjectResult(ResultCode.SUCCESS, "删除数据成功", true);
        }
    }

    /**
     * @param noticeIDs 待删除的通知ID
     * @return 是否删除成功
     */
    @PostMapping("/deleteSome")
    public Object removeSomeNotice(String noticeIDs) {
        String[] ids = noticeIDs.split(",");
        for (String noticeId : ids) {
            noticeJpa.delete(noticeJpa.findByNoticeId(Long.parseLong(noticeId)));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
    }

    /**
     * @param date 日期
     * @return 根据日期来查询得到的部分通知
     */
    @PostMapping("/queryByTime")
    public Object queryByTime(String date) {
        return new JsonObjectResult(ResultCode.SUCCESS, "查询成功", noticeJpa.findByNoticeTimeLike(date + "%"));
    }
}
