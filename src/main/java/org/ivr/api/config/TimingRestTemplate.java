package org.ivr.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

/**
 * @author zhangyaoxin@yiwise.com
 * @description TODO
 * @create 2019/04/30
 **/
@Component
public class TimingRestTemplate extends RestTemplate {

    private static final Logger logger = LoggerFactory.getLogger(TimingRestTemplate.class);

    @Autowired
    public TimingRestTemplate(HttpMessageConverter gsonHttpMessageConverter) {
        super();
        this.getMessageConverters().add(gsonHttpMessageConverter);
        MappingJackson2HttpMessageConverter chatConverter = new MappingJackson2HttpMessageConverter();
        chatConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        this.getMessageConverters().add(chatConverter);
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        logger.info("{} {}", method.toString(), url);
        long start = System.currentTimeMillis();
        T response = super.doExecute(url, method, requestCallback, responseExtractor);
        logger.info("{} {} spend: {}ms", method.toString(), url, System.currentTimeMillis() - start);
        return response;
    }
}
