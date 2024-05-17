package com.app.businessBridge;

import com.app.businessBridge.domain.Article.Controller.ApiV1ArticleController;
import com.app.businessBridge.domain.Article.Entity.Article;
import com.app.businessBridge.domain.Article.Service.ArticleService;
import com.app.businessBridge.domain.chatLog.entity.ChatLog;
//import com.app.businessBridge.global.handler.WebSocketChatHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.util.List;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class BusinessBridgeApplicationTests {

//    @Autowired
//    WebSocketChatHandler webSocketChatHandler;
    @Test
    void contextLoads() {
    }

    @Test
    void test01() {

    }

    @Test
    public  void  plus () {
        int sum = 10+ 10 ;
        assertEquals(20,sum);
    }


}


