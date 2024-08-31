package com.stc.exporter.config.database.production;

import com.stc.exporter.config.database.DataBaseSessionTemplate;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("production")
@Component
public class ProductionDataBaseSessionTemplateImpl implements DataBaseSessionTemplate {

    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public ProductionDataBaseSessionTemplateImpl(@Qualifier("productionSqlSessionTemplate") SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }
}
