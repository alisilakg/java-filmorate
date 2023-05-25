//package ru.yandex.practicum.filmorate.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//public class AppConfig {
//    public static final String JDBC_URL = "jdbc:h2:file:./db/filmorate";
//    public static final String JDBC_USERNAME = "sa";
//    public static final String JDBC_PASSWORD = "password";
//    public static final String JDBC_DRIVER = "org.h2.Driver";
//
//    //создаём источник данных
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(JDBC_DRIVER);
//        dataSource.setUrl(JDBC_URL);
//        dataSource.setUsername(JDBC_USERNAME);
//        dataSource.setPassword(JDBC_PASSWORD);
//        return dataSource;
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(dataSource());
//    }
//}
