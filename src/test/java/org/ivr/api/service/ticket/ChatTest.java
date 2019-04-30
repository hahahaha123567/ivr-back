package org.ivr.api.service.ticket;

import org.ivr.api.service.chat.ChatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhangyaoxin@yiwise.com
 * @description TODO
 * @create 2019/04/30
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ChatTest {

    @Autowired
    private ChatService chatService;

    @Test
    public void test() {
        String s = chatService.chat("妮可妮可妮", String.valueOf(25252));
        System.out.println(s);
    }
}
