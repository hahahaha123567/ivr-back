package org.ivr.api.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.ivr.api.model.Response;
import org.ivr.api.service.chat.ChatService;
import org.ivr.api.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/08
 **/
@RestController
@RequestMapping("menu")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuController {

    MenuService menuService;
    ChatService chatService;

    @Autowired
    public MenuController(MenuService menuService, ChatService chatService) {
        this.menuService = menuService;
        this.chatService = chatService;
    }

    @GetMapping("navigate")
    public Response navigate(@RequestParam String input, @RequestParam Integer requestId) {
        String s = menuService.navigate(input, requestId);
        return Response.success(s);
    }

    @GetMapping("chat")
    public Response chat(@RequestParam String input, @RequestParam Integer requestId) {
        String s = chatService.chat(input, String.valueOf(requestId));
        return Response.success(s);
    }
}
