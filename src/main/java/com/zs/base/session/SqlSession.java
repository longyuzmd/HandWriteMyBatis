package com.zs.base.session;

import java.util.List;

/**
 * @ProjectName: HandWritingMyBatis
 * @Package: com.zs.base.session
 * @ClassName: SqlSession
 * @Author: longyuzmd
 * @Description:
 * @Date: 2021/1/31 8:17 下午
 * @Version: 1.0
 */
public interface SqlSession {

     <T> T selectOne(String statement, Object parameter);


     <E> List<E> selectList(String statement, Object parameter);


     <T> T getMapper(Class<T> type);
}
