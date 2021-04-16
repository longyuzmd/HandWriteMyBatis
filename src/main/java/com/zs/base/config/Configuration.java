package com.zs.base.config;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: HandWritingMyBatis
 * @Package: com.zs.base.config
 * @ClassName: Configuration
 * @Author: longyuzmd
 * @Description: 配置信息封装对象
 * @Date: 2021/1/25 3:05 下午
 * @Version: 1.0
 */
@Data
public class Configuration {

    private String jdbcDriver;

    private String jdbcUrl;

    private String jdbcUsername;

    private String jdbcPassword;

    private Map<String,MappedStatement> mappedStatements = new HashMap<String,MappedStatement>();

}
