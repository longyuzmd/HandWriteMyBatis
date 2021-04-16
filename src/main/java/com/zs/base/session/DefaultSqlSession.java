package com.zs.base.session;

import com.zs.base.bind.MapperProxy;
import com.zs.base.config.Configuration;
import com.zs.base.config.MappedStatement;
import com.zs.base.executor.DefaultExecutor;
import com.zs.base.executor.Executor;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @ProjectName: HandWritingMyBatis
 * @Package: com.zs.base.session
 * @ClassName: DefaultSqlSession
 * @Author: longyuzmd
 * @Description: sqlSession 的临时实现类
 * @Date: 2021/1/31 8:23 下午
 * @Version: 1.0
 */

/**
 * 1、对外提供访问的api
 *
 * 2、对内将请求转发给executor
 */
public class DefaultSqlSession implements SqlSession {

    private final Configuration conf;

    private Executor executor;

    public DefaultSqlSession(Configuration conf) {
        super();
        this.conf = conf;
        executor = new DefaultExecutor(conf);
    }


    public <T> T selectOne(String statement, Object parameter) {
        List<Object> selectList = this.selectList(statement, parameter);
        if(selectList == null || selectList.size() == 0){
            return null;
        }
        if(selectList.size() == 1){
            return (T) selectList.get(0);
        }else{
            throw new RuntimeException("Too Many Results");
        }
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        MappedStatement ms = conf.getMappedStatements().get(statement);
        return executor.query(ms,parameter);
    }

    public <T> T getMapper(Class<T> type) {
        MapperProxy mapperProxy = new MapperProxy(this);
        return (T) Proxy.newProxyInstance(type.getClassLoader(),new Class[]{type},mapperProxy);
    }
}
