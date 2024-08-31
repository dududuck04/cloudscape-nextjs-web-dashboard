package com.stc.exporter.config.database;

import org.mybatis.spring.SqlSessionTemplate;

public interface DataBaseSessionTemplate {
    SqlSessionTemplate getSqlSessionTemplate();
}
