package org.ivr.api.service.menu;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/02
 **/
public interface MenuService {

    /**
     * 规则引擎根据用户输入匹配最可能访问的服务
     *
     * @param input     用户语音输入转成的文字
     * @param requestId 请求id
     * @return tts的输入文字
     */
    String navigate(String input, Integer requestId);
}
