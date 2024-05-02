package com.sunflower.icpc_volunteer_management.demo.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author YG
 */
@RestController
@RequestMapping("/websocket/{userId}")
public class WebSocketController {
    @Autowired
    WebSocket webSocket;

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam String message,Integer toId) throws IOException {
        webSocket.sendMessage(message,toId);
        return "success";
    }
}
