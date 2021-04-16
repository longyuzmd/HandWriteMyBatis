package com.zs.base.executor;

import com.zs.base.config.Configuration;
import com.zs.base.config.MappedStatement;
import com.zs.base.util.ReflectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: HandWritingMyBatis
 * @Package: com.zs.base.executor
 * @ClassName: DefaultExecutor
 * @Author: longyuzmd
 * @Description: 执行器临时实现类
 * @Date: 2021/1/31 9:18 下午
 * @Version: 1.0
 */
public class DefaultExecutor implements Executor {

    private final Configuration conf;

    public DefaultExecutor(Configuration conf) {   // 实例化对象
        super();
        this.conf = conf;
    }

    // 底层jdbc规范访问数据库
    public <E> List<E> query(MappedStatement ms, Object parameter) {
//        System.out.println(ms.getSql());
//        System.out.println(ms.getResultType());
        ArrayList<E> ret = new ArrayList<E>();  // 定义返回结果集
        try {
            Class.forName(conf.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(conf.getJdbcUrl(),
                    conf.getJdbcUsername(), conf.getJdbcPassword());
            preparedStatement = connection.prepareStatement(ms.getSql());
            parameterize(preparedStatement,parameter);
            resultSet = preparedStatement.executeQuery();  // 执行sql得到结果集
            handlerResultSet(resultSet,ret,ms.getResultType());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    // 处理占位符的问题，防止sql注入的问题
    private void parameterize(PreparedStatement preparedStatement, Object parameter) throws SQLException{
        if(parameter instanceof Integer){
            preparedStatement.setInt(1, Integer.parseInt(parameter.toString()));
        }else if(parameter instanceof Long){
            preparedStatement.setLong(1, Long.parseLong(parameter.toString()));
        }else if(parameter instanceof String){
            preparedStatement.setString(1,(String) parameter);
        }
    }

    // 处理结果集的封装
    private <E> void handlerResultSet(ResultSet resultSet, List<E> ret,String className){
        Class<E> clazz = null;
        try {
            clazz = (Class<E>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            while (resultSet.next()){
                // 通过反射实例化对象
                Object entity = clazz.newInstance();
                // 使用反射工作将resultSet中数据填充到entity
                ReflectionUtil.setPropToBeanFromResultSet(entity,resultSet);
                // 将对象加到返回集合中
                ret.add((E) entity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
