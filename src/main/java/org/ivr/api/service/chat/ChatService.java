package org.ivr.api.service.chat;

/**
 * @author zhangyaoxin@yiwise.com
 * @description TODO
 * @create 2019/04/30
 **/
public interface ChatService {

    /**
     * 聊天服务
     *
     * @param input  输入文本
     * @param userId 标识当前的用户(区分上下文)
     * @return 输出文本
     */
    String chat(String input, String userId);
}
