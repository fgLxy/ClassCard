package com.school.management.api.utils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class GsonUtil {

    private static final Gson GSON = new Gson();

    public static String parseObject (boolean isWarning, Object o) {
        Map<String, Object> map = new HashMap<>();
        if (isWarning) {
            map.put("state", 500);
            map.put("data", null);
            map.put("message", "数据发送失败，请核对后重试");
        } else {
            map.put("state", 200);
            map.put("data", o);
            map.put("message", "数据发送成功，请注意接收");
        }
        return GSON.toJson(map);
    }

    public static String parseObject (Object o) {
        return parseObject(false, o);
    }

}
