package com.zs.base.bind;

import com.zs.base.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @ProjectName: HandWritingMyBatis
 * @Package: com.zs.base.bind
 * @ClassName: MapperProxy
 * @Author: longyuzmd
 * @Description: mapper业务实现
 * @Date: 2021/1/31 10:00 下午
 * @Version: 1.0
 */
public class MapperProxy implements InvocationHandler {

    private SqlSession session;

    public MapperProxy(SqlSession session) {
        super();
        this.session = session;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 根据返回类型判断调用的方法
        Class<?> returnType = method.getReturnType();
        if(Collection.class.isAssignableFrom(returnType)){
            return session.selectList(method.getDeclaringClass().getName()+ "." + method.getName()
                    ,args == null ? null : args[0]);   // iBatis 编程模式
        }else{
            return session.selectOne(method.getDeclaringClass().getName() + "." + method.getName()
                    ,args == null ? null : args[0]);
        }
    }
}
