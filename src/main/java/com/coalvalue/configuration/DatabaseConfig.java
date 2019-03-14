package com.coalvalue.configuration;

//import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;

import javax.sql.DataSource;

/**
 * Created by zohu on 5/29/2015.
 */
@Configuration
//@ConfigurationProperties(prefix = DataSourceProperties.PREFIX)
public class DatabaseConfig {

/*
    private DataSource dataSource;

    @Bean
    @ConfigurationProperties( prefix="spring.datasource")
    public DBConfig dBConfig(){
        return new DBConfig();

    }*/
/*        ds.setDriver(dBConfig().getDriverClassName());
        ds.setUser(dBConfig.getUsername());
        ds.setDriverUrl(dBConfig.getUrl());
        ds.setPassword(dBConfig.getPassword());*/
 /*   public ProxoolDataSource proxoolDataSource(){
        ProxoolDataSource ds = new ProxoolDataSource();
        DBConfig dBConfig = null;//dBConfig();

        ds.setMaximumConnectionCount(10);
        ds.setMinimumConnectionCount(2);
        ds.setSimultaneousBuildThrottle(5);

        ds.setDriver("com.mysql.cj.jdbc.Driver");
        ds.setUser("root");
        ds.setDriverUrl("jdbc:mysql://mysql:3306/storage?serverTimezone=Asia/Shanghai&characterEncoding=UTF-8&useSSL=false");
        ds.setPassword("123456");



        System.out.println("----------------------"+dBConfig.toString());
        return ds;
    }*/





    //自定义的DataSource，默认方法名就是bean的id
    @Bean
    //@Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
/*        try {
            this.dataSource = new LazyConnectionDataSourceProxy(proxoolDataSource());
            return dataSource;
        }catch (NullPointerException e) {
            System.out.printf("we get null dataSource");
            e.printStackTrace();
            return null;
        }*/
    }




/*


    @Bean(name = "uuidNoGenerator")
    public MySQLMaxValueIncrementer getUuidNoGenerator() {
        MySQLMaxValueIncrementer mySQLMaxValueIncrementer = new MySQLMaxValueIncrementer();
        mySQLMaxValueIncrementer.setColumnName("gen_uuidNo");
        mySQLMaxValueIncrementer.setDataSource(dataSource());
        //    mySQLMaxValueIncrementer.setCacheSize(100000);
        mySQLMaxValueIncrementer.setIncrementerName("tb_generator");
        mySQLMaxValueIncrementer.setPaddingLength(32);
        return mySQLMaxValueIncrementer;
    }

*/



    @Bean(name = "transportNoGenerator")
    public MySQLMaxValueIncrementer getTransportNoGenerator() {
        MySQLMaxValueIncrementer mySQLMaxValueIncrementer = new MySQLMaxValueIncrementer();
        mySQLMaxValueIncrementer.setColumnName("gen_transportyNo");
        mySQLMaxValueIncrementer.setDataSource(dataSource());
        //    mySQLMaxValueIncrementer.setCacheSize(100000);
        mySQLMaxValueIncrementer.setIncrementerName("tb_generator");
        mySQLMaxValueIncrementer.setPaddingLength(8);
        return mySQLMaxValueIncrementer;
    }



    @Bean(name = "inventoryTransferGenerator")
    public MySQLMaxValueIncrementer getInventoryTransferGenerator() {
        MySQLMaxValueIncrementer mySQLMaxValueIncrementer = new MySQLMaxValueIncrementer();
        mySQLMaxValueIncrementer.setColumnName("gen_inventory_transfer_no");
        mySQLMaxValueIncrementer.setDataSource(dataSource());
        //    mySQLMaxValueIncrementer.setCacheSize(100000);
        mySQLMaxValueIncrementer.setIncrementerName("tb_generator");
        mySQLMaxValueIncrementer.setPaddingLength(8);
        return mySQLMaxValueIncrementer;
    }










}
