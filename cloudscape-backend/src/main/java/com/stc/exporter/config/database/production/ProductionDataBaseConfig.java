package com.stc.exporter.config.database.production;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Profile("production") // production 프로필이 활성화되었을 때만 이 설정을 적용
@MapperScan(basePackages = {
        "com.cbm.saekalpi.app.user.dao",
        "com.cbm.saekalpi.app.color.dao",
        "com.cbm.saekalpi.app.diary.dao",
        "com.cbm.saekalpi.app.emotion.dao",
        "com.cbm.saekalpi.app.keyword.dao"},
        sqlSessionFactoryRef="production")
@EnableTransactionManagement
public class ProductionDataBaseConfig {

    public interface DatabaseSessionTemplate {
        SqlSessionTemplate getSqlSessionTemplate();
    }



    @Bean(name="productionDataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource productionDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="productionSqlSessionFactory")
    public SqlSessionFactory productionSqlSessionFactory(@Qualifier("productionDataSource") DataSource productionDataSource, ApplicationContext applicationContext) throws Exception{
        //세션 생성 시, 빌드된 DataSource를 세팅하고 SQL문을 관리할 mapper.xml의 경로를 알려준다.
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(productionDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/employee.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="productionSqlSessionTemplate")
    public SqlSessionTemplate productionSqlSessionTemplate(SqlSessionFactory productionSqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(productionSqlSessionFactory);
    }


}
