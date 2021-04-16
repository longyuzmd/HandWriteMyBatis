package com.zs.base.session;

/**
 * @ProjectName: HandWritingMyBatis
 * @Package: com.zs.base.session
 * @ClassName: SqlSessionFactory
 * @Author: longyuzmd
 * @Description: 会话工厂
 * @Date: 2021/1/31 7:31 下午
 * @Version: 1.0
 */

import com.zs.base.config.Configuration;
import com.zs.base.config.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * 1、在初始化的时候把配置信息加载到configuration
 *
 * 2、生成sqlSession
 */
public class SqlSessionFactory {

    // 全局唯一的一个对象，单例
    private final Configuration conf = new Configuration();

    public SqlSessionFactory(){
        loadDbInfo();  // 加载数据库连接信息
        loadMappersInfo();   // 读取xml
    }

    // 记录mapper文件存放位置
    public static final String MAPPER_CONFIG_LOCATION = "mapper";

    // 记录 db.properties 文件位置
    public static final String DB_CONFIG_FILE = "db.properties";

    // 加载数据库配置信息
    private void loadDbInfo() {
        InputStream dbIn = SqlSessionFactory.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILE);
        Properties p = new Properties();

        try {
            p.load(dbIn);   // 将配置信息放入properties对象
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 将数据库配置信息注入config对象
        conf.setJdbcDriver(p.get("jdbc.driver").toString());
        conf.setJdbcUrl(p.get("jdbc.url").toString());
        conf.setJdbcUsername(p.get("jdbc.username").toString());
        conf.setJdbcPassword(p.get("jdbc.password").toString());
    }

    // 加载指定文件夹下面的所有mapper.xml
    private void loadMappersInfo() {
        URL resources = null;
        resources = SqlSessionFactory.class.getClassLoader().getResource(MAPPER_CONFIG_LOCATION);
        File mappers = new File(resources.getFile()); // 获取指定文件加的信息
        if(mappers.isDirectory()){
            File[] listFiles = mappers.listFiles();
            for(File file : listFiles){
                loadMapperInfo(file);
            }
        }
    }

    // 加载指定mapper.xml文件信息
    private void loadMapperInfo(File file) {
        // 创建saxReader对象
        SAXReader reader = new SAXReader();
        // 通过read方法读取文件 得到一个document对象
        Document document = null;

        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // 获取根节点元素对象 mapper
        Element root = document.getRootElement();
        // 获取命名空间 namespace
        String namespace = root.attribute("namespace").getData().toString();
        // 获取select子节点列表
        List<Element> selects = root.elements("select");

        // 遍历selects 将信息加载到mapperStatement, 并注册到configuration对象
        for(Element e : selects){
            MappedStatement mappedStatement = new MappedStatement();
            String id = e.attribute("id").getData().toString();
            String resultType = e.attribute("resultType").getData().toString();
            String sql = e.getData().toString();
            String sourceId = namespace + "." + id;

            mappedStatement.setNamespace(namespace);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSourceId(sourceId);
            mappedStatement.setSql(sql);

            // 注册到conf 对象里面去
            conf.getMappedStatements().put(sourceId,mappedStatement);
        }
    }

    // 生产sqlSession
    public SqlSession openSession(){
        return new DefaultSqlSession(conf);
    }
}
