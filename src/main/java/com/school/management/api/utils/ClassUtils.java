package com.school.management.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;

public class ClassUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtils.class);

    public static Method[] getGetter(@NotNull Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        Method[] getter = new Method[(methods.length / 2) + 1];
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                getter[i] = method;
            }
        }
        return getter;
    }

    public static Method[] getSetter(@NotNull Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        Method[] setter = new Method[(methods.length / 2) + 1];
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String methodName = method.getName();
            if (methodName.startsWith("set")) {
                setter[i] = method;
            }
        }
        return setter;
    }

    public static Method[] getSetPhoto(@NotNull Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        Method[] setter = new Method[methods.length / 2];
        for (int j = 0; j < setter.length; j++) {
            for (Method method : methods) {
                if (method.getName().startsWith("set")) {
                    setter[j] = method;
                    break;
                }
            }
        }
        return setter;
    }
}
