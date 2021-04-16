package com.zs.base.mapper;

import com.zs.base.entity.Person;

import java.util.List;

/**
 * @ProjectName: MyBatisBase
 * @Package: com.zs.base.mapper
 * @ClassName: PersonMapper
 * @Author: longyuzmd
 * @Description: mapper接口
 * @Date: 2021/1/25 11:01 上午
 * @Version: 1.0
 */
public interface PersonMapper {

    Person selectPerson(String id);

    List<Person> selectAllPerson();

}
