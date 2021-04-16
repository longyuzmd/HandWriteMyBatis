package com.zs.base.executor;

import com.zs.base.config.MappedStatement;

import java.util.List;

/**
 * @ProjectName: HandWritingMyBatis
 * @Package: com.zs.base.executor
 * @ClassName: Executor
 * @Author: longyuzmd
 * @Description: mybatis 执行器
 * @Date: 2021/1/31 9:16 下午
 * @Version: 1.0
 */
public interface Executor {

    <E> List<E> query(MappedStatement ms, Object parameter);

}
