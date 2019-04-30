package org.ivr.api.config;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.ivr.api.exception.MissingArgException;
import org.ivr.api.exception.UnrecognizedException;
import org.ivr.api.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/02
 **/
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GlobalExceptionResolver extends SimpleMappingExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
    private static final String RESPONSE_KEY = "responseKey";

    Gson gson;

    @Autowired
    public GlobalExceptionResolver(Gson gson) {
        this.gson = gson;
    }

    @Override
    public ModelAndView resolveException(@NonNull HttpServletRequest request, @Nullable HttpServletResponse response, Object handler, @NonNull Exception ex) {

        Response myResponse;
        if (ex instanceof MissingArgException) {
            myResponse = Response.fail(400, ex.getMessage());
        } else if (ex instanceof UnrecognizedException) {
            myResponse = Response.fail(401, ex.getMessage());
        } else {
            logger.info("Request URI:{}", request.getRequestURI());
            logger.info("Request method:{}", request.getMethod());
            logger.info("Request Param:{}", gson.toJson(request.getParameterMap()));
            logger.error("", ex);
            myResponse = Response.fail(402, ex.getMessage());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(abstractView());
        modelAndView.addObject(RESPONSE_KEY, myResponse);

        return modelAndView;
    }

    @Bean
    public AbstractView abstractView() {
        return new AbstractView() {
            @Override
            protected void renderMergedOutputModel(@NonNull Map<String, Object> map,
                                                   @Nullable HttpServletRequest httpServletRequest,
                                                   @NonNull HttpServletResponse httpServletResponse) throws Exception {
                httpServletResponse.setContentType("text/json; charset=UTF-8");
                PrintWriter out = httpServletResponse.getWriter();
                out.print(gson.toJson(map.get(RESPONSE_KEY)));
            }
        };
    }
}
