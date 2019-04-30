package org.ivr.api.service.chat;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.ivr.api.dal.chat.vo.ChatRequestVO;
import org.ivr.api.dal.chat.vo.ChatResponseVO;
import org.ivr.api.exception.UnrecognizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangyaoxin@yiwise.com
 * @description TODO
 * @create 2019/04/30
 **/
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatServiceImpl implements ChatService {

    static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    RestTemplate timingRestTemplate;
    @Value("${tuling.apiKey}")
    String apiKey;
    @Value("${tuling.url}")
    String url;

    @Autowired
    public ChatServiceImpl(RestTemplate timingRestTemplate) {
        this.timingRestTemplate = timingRestTemplate;
    }

    @Override
    public String chat(String input, String userId) {
        ChatRequestVO request = new ChatRequestVO(input, apiKey, userId);
        ChatResponseVO response;
        try {
            response = timingRestTemplate.postForObject(url, request, ChatResponseVO.class, (Object) null);
            if (response == null) {
                logger.error("调用图灵机器人接口失败, response反序列化失败");
                throw new UnrecognizedException("机器人心情不好, 不想跟你聊天");
            } else {
                return response.getResults().get(0).getValues().entrySet().toString();
            }
        } catch (Exception e) {
            logger.error("调用图灵机器人接口失败", e);
            throw new UnrecognizedException("机器人心情不好, 不想跟你聊天");
        }
    }
}
