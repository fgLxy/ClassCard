package com.school.management.api.utils;

import java.io.File;

public class StringUtils extends org.springframework.util.StringUtils {

    /**
     * 返回斜杠拼接的字符串
     * @param args
     * @return
     */
    public static String mergeStringWithSeparator(String...args){
        StringBuilder sb=new StringBuilder();
        for (String arg : args) {
            sb.append(arg);
            sb.append(File.separator);
        }

        return sb.substring(0, sb.length()-1).toString();
    }

}
