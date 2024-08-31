package com.stc.exporter.config.database.local;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@Profile("local") // local 프로필이 활성화되었을 때만 이 설정을 적용
@MapperScan(basePackages = {
        "com.cbm.saekalpi.app.user.dao",
        "com.cbm.saekalpi.app.color.dao",
        "com.cbm.saekalpi.app.diary.dao",
        "com.cbm.saekalpi.app.emotion.dao",
        "com.cbm.saekalpi.app.keyword.dao"},
        sqlSessionFactoryRef="localSqlSessionFactory")
@EnableTransactionManagement
public class LocalDataBaseConfig {

    public interface DatabaseSessionTemplate {
        SqlSessionTemplate getSqlSessionTemplate();
    }

    @Bean(name="localDataSource")
    public DataSource localDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;INIT=RUNSCRIPT FROM 'classpath:/mybatis/local/init.sql'");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean(name="localSqlSessionFactory")
    public SqlSessionFactory localSqlSessionFactory(@Qualifier("localDataSource") DataSource localDataSource, ApplicationContext applicationContext) throws Exception{
        //세션 생성 시, 빌드된 DataSource를 세팅하고 SQL문을 관리할 mapper.xml의 경로를 알려준다.
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(localDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mybatis/local/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="localSqlSessionTemplate")
    public SqlSessionTemplate localSqlSessionTemplate(SqlSessionFactory localSqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(localSqlSessionFactory);
    }

}