package com.pj.user.Utils;

import com.pj.auth.pojo.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectTrim {

    public static Object beanAttributeValueTrim(Object obj){
        Class<?> objClass = obj.getClass();
        Field[] files = objClass.getDeclaredFields();
        Method[] methods  = objClass.getDeclaredMethods();
        for (Field f : files){
            f.setAccessible(true);
            if(f.getType().getSimpleName().toString().equals("String") ){
                String fieldSetName = parSetName(f.getName());
                String fieldGetName = parGetName(f.getName());
                try {
                    Method method = objClass.getDeclaredMethod(fieldSetName, f.getType());
                    Method method1 = objClass.getDeclaredMethod(fieldGetName);
                    Object o = method1.invoke(obj);
                    if(o == null){
                        continue;
                    }
                    method.invoke(obj,o.toString().trim());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    /**
     * 拼接在某属性的 set方法
     *
     * @param fieldName
     * @return String
     */
    public static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "set"
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }
    /**
     * 拼接某属性的 get方法
     *
     * @param fieldName
     * @return String
     */
    public static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "get"
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }
}
