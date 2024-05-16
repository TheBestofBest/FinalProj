package com.app.businessBridge;

import com.app.businessBridge.domain.chatLog.entity.ChatLog;
//import com.app.businessBridge.global.handler.WebSocketChatHandler;
import com.app.businessBridge.domain.confirm.request.ConfirmRequest;
import com.app.businessBridge.domain.rebate.service.RebateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

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

//    @Test
//    void howManyRebates() {
//        int sum = this.rebateService.findAll().size();
//
//        assertEquals(20,sum);
//    }
}
