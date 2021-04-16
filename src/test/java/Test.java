import com.zs.base.entity.Person;
import com.zs.base.mapper.PersonMapper;
import com.zs.base.session.SqlSession;
import com.zs.base.session.SqlSessionFactory;

import java.util.List;

/**
 * @ProjectName: HandWritingMyBatis
 * @Package: PACKAGE_NAME
 * @ClassName: Test
 * @Author: longyuzmd
 * @Description: 测试手撸代码的成果
 * @Date: 2021/1/31 8:54 下午
 * @Version: 1.0
 */
public class Test {

    @org.junit.Test
    public void testCode(){

        // 第一阶段 构建sqlSessionFactory生成sqlSession
        SqlSessionFactory factory = new SqlSessionFactory();

        SqlSession sqlSession = factory.openSession();

        System.out.println(sqlSession);

        // 第二阶段 动态代理获取mapper接口实现类
        PersonMapper mapper = sqlSession.getMapper(PersonMapper.class);

        // 第三阶段 数据库读写操作
        Person person = mapper.selectPerson("1");
        System.out.println(person.toString());

        System.out.println("==========================");

        List<Person> list = mapper.selectAllPerson();

        for(Person p:list){
            System.out.println(p.toString());
        }

    }
}
