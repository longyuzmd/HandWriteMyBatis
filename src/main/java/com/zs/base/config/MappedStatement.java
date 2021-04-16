package com.zs.base.config;

import lombok.Data;

/**
 * @ProjectName: HandWritingMyBatis
 * @Package: com.zs.base.config
 * @ClassName: MappedStatement
 * @Author: longyuzmd
 * @Description: sql存储封装对象
 * @Date: 2021/1/25 3:05 下午
 * @Version: 1.0
 */
@Data
public class MappedStatement {

    private String namespace;

    private String sourceId;

    private String resultType;

    private String sql;
}
