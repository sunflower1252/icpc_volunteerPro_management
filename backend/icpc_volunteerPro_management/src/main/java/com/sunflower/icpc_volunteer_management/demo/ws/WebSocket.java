package com.sunflower.icpc_volunteer_management.demo.ws;

import cn.dev33.satoken.stp.StpUtil;

import com.alibaba.fastjson2.JSONObject;
import com.sunflower.icpc_volunteer_management.demo.entity.Message;
import com.sunflower.icpc_volunteer_management.demo.mapper.MessageMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author YG
 */

@Component
@Slf4j
@ServerEndpoint("/websocket/{userId}")  // 接口路径 ws://localhost:8080/webSocket/userId;
public class WebSocket {

    @Autowired
    MessageMapper messageMapper;
    /**
     * 用户id
     */
    public Integer userId;


    /**
     * 设置一次性存储数据的list的长度为固定值，每当list的长度达到固定值时，向数据库存储一次
     */
    private static final Integer LIST_SIZE = 3;

    /**
     * 新建list集合存储数据
     */
    private static ConcurrentLinkedQueue<Message> MessageList = new ConcurrentLinkedQueue<>();


    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    //  注：底下WebSocket是当前类名
    //  注：这个集合用于存放每个客户端的websocket连接
    public static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    /**
     * map(username,websocket)作为对象添加到集合中
     *  注：这个集合用来存储在线用户的信息
     */
    private static ConcurrentHashMap<Integer, Session> sessionPool = new ConcurrentHashMap<>();


    /**
     * 监听连接（如果有新用户发生连接，立刻执行此方法）
     *
     * @param session session
     */
    @OnOpen
    public void onOpen(Session session){
        try{
            //赋值用户id和session
            this.userId = StpUtil.getLoginIdAsInt();
            /**
             * session会话，通过session给客户端发送数据
             */
            webSockets.add(this);
            sessionPool.put(userId, session);
            log.info("【新的websocket连接，用户名】："+userId);
            log.info("【当前在线人数为】："+sessionPool.size());

            // 上线时查看rabbitmq中是否有离线时的消息
            /*
              假设rabbitmq存在离线消息，则发送给用户
              此处省略代码
             */

        }catch (Exception e){
            log.error("Error 【opening】 WebSocket connection：", e);
        }
    }

    /**
     * 监听连接断开（有用户退出，会立马到来执行这个方法）
     */
    @OnClose
    public void onClose() {
        try{
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            log.info("【websocket消息：用户已断开，id】："+userId);
        }catch (Exception e){
            log.error("Error 【closing】 WebSocket connection：",e);
        }
    }

    @OnError
    public void onError(Throwable error) {
        log.error("服务端发生了错误" + error.getMessage());
    }

    @OnMessage
    public void onMessage(String message,Session session){
        try {
            //获取当前用户id
            Integer userId = StpUtil.getLoginIdAsInt();
            log.info("【来自客户端的消息】：{}，客户端的id：{}", message, userId);
            JSONObject jsonObject = JSONObject.parseObject(message);
            //发送的内容
            String textMessage = jsonObject.getString("message");
            //获取接收人id
            int toId = jsonObject.getInteger("to");

            //新建message对象
            Message message1 = new Message();
            message1.setFromId(userId);
            message1.setStatus(0);
            message1.setToId(toId);
            if(toId<userId){
                message1.setConversationId(toId+"_"+userId);
            }else{
                message1.setConversationId(userId+"_"+toId);
            }
            message1.setCreateTime(new Date());
            message1.setContent(textMessage);

            //批量保存信息
            //将每个信息保存道list中
            //使用批量保存
            if(MessageList.size()==LIST_SIZE){
                messageMapper.saveBatch(MessageList);
                MessageList.clear();
            }

        }catch (Exception e){
            log.error("Error 【OnMessage】:",e);
        }
    }

    public void sendMessage(String message,Integer toId) throws IOException {
        try {
            //遍历map集合，如果对方在线，那么直接发送，如果对方不在线，存储到rabbitMQ消息队列中
            Session session1 = sessionPool.get(toId);
            //如果对方在线，直接通过websocket发送消息
            if(session1 != null && session1.isOpen()){
                log.info("【websocket消息】 发送消息给："+toId+" ，内容为："+message);
                session1.getBasicRemote().sendText(message);
            }
            //如果对方不在线，存储到rabbitMQ消息队列中
            else{
                log.info("【websocket】发送消息，对方不在线");
                /*
                 * 假设现在已经集成了rabbitmq的代码，这里就先不写了
                 */
            }
        } catch (Exception e) {
            log.error("Error 【sendMessage】:", e);
        }
    }
}
