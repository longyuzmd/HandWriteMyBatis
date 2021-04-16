package com.zs.base.util;

import com.zs.base.entity.Person;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ProjectName: HandWritingMyBatis
 * @Package: com.zs.base.util
 * @ClassName: ReflectionUtil
 * @Author: longyuzmd
 * @Description: 反射工具类
 * @Date: 2021/2/1 1:10 下午
 * @Version: 1.0
 */
public class ReflectionUtil {

    /**
     *
     * @param bean 目标对象
     * @param propName  属性名
     * @param value  值
     */
    public static void setPropToBean(Object bean,String propName,Object value){
        Field f;
        try {
            f = bean.getClass().getDeclaredField(propName);
            f.setAccessible(true);  // 将字段设置成可通过反射访问
            f.set(bean,value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param entity
     * @param resultSet
     * @throws SQLException
     */
    public static void setPropToBeanFromResultSet(Object entity, ResultSet resultSet) throws SQLException {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for(Field f : declaredFields){
            if(f.getType().getSimpleName().equals("String")){
                setPropToBean(entity,f.getName(),resultSet.getString(f.getName()));
            }else if(f.getType().getSimpleName().equals("Long")){
                setPropToBean(entity,f.getName(),resultSet.getLong(f.getName()));
            }else if(f.getType().getSimpleName().equals("Integer")){
                setPropToBean(entity,f.getName(),resultSet.getInt(f.getName()));
            }
        }
    }

    public static void main(String[] args) {
        Person person = new Person();
        setPropToBean(person,"age","13");
        System.out.println(person.toString());
    }
}
