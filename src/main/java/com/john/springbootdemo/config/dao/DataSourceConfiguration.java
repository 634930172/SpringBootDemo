package com.john.springbootdemo.config.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/1/30 10:40
 * <p/>
 * Description:数据库连接配置
 */
//配置mybatis mapper的扫描路径
@Configuration
@MapperScan("com.john.springbootdemo.dao")
public class DataSourceConfiguration {
    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String user;
    @Value("${jdbc.password}")
    private String passWord;

    @Bean(name = "dataSource")
    public ComboPooledDataSource crateDataSource() throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource=new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(jdbcDriver);
        comboPooledDataSource.setJdbcUrl(jdbcUrl);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(passWord);
        //关闭连接后不自动提交
        comboPooledDataSource.setAutoCommitOnClose(false);
        return  comboPooledDataSource;
    }

}
