package org.ivr.api.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.ivr.api.dal.ticket.serializer.RailwayResponseDeserializer;
import org.ivr.api.dal.ticket.vo.QueryByTrainNoData;
import org.ivr.api.dal.ticket.vo.QueryData;
import org.ivr.api.dal.ticket.vo.QueryTicketPriceData;
import org.ivr.api.dal.ticket.vo.RailwayResponseVO;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyaoxin@yiwise.com
 * @description mvc config
 * @create 2019/04/02
 **/
@Configuration
@EnableWebMvc
@MapperScan("org.ivr.api.dal.*.mapper")
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(new TypeToken<RailwayResponseVO<QueryData>>() {
                }.getType(), new RailwayResponseDeserializer())
                .registerTypeAdapter(new TypeToken<RailwayResponseVO<QueryByTrainNoData>>() {
                }.getType(), new RailwayResponseDeserializer())
                .registerTypeAdapter(new TypeToken<RailwayResponseVO<QueryTicketPriceData>>() {
                }.getType(), new RailwayResponseDeserializer())
                .create();
    }

    @Bean
    public GlobalExceptionResolver globalExceptionResolver() {
        return new GlobalExceptionResolver(gson());
    }

    @Bean
    public DataSource dataSource(@Value("${db.url}") String url,
                                 @Value("${db.username}") String username,
                                 @Value("${db.password}") String password) throws Exception {
        Map<String, String> properties = ImmutableMap.of(
                DruidDataSourceFactory.PROP_URL, url,
                DruidDataSourceFactory.PROP_USERNAME, username,
                DruidDataSourceFactory.PROP_PASSWORD, password
        );
        DruidDataSource druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        druidDataSource.setName("mysql-ivr");
        druidDataSource.getConnection().close();
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        VFS.addImplClass(SpringBootVFS.class);

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath*:/mapper/**/*Mapper.xml"));
        bean.setTypeHandlersPackage("org.ivr.api.dak.typehandler");
        bean.setPlugins(new Interceptor[]{});
        bean.setDataSource(dataSource);

        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new GsonHttpMessageConverter());
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(globalExceptionResolver());
    }
}
