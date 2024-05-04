package com.sunflower.icpc_volunteer_management.demo.ws;

import com.alibaba.fastjson2.JSONObject;
import com.sunflower.icpc_volunteer_management.demo.entity.Message;
import com.sunflower.icpc_volunteer_management.demo.mapper.MessageMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author YG
 */

@Component
@Slf4j
@ServerEndpoint("/websocket/{userId}")  // 接口路径 ws://localhost:8080/webSocket/userId;
public class WebSocket {

    //注入mapper
    private static ApplicationContext applicationContext;
    private MessageMapper messageMapper;
    //注入redis
    private StringRedisTemplate stringRedisTemplate;
    public static void setApplicationContext(ApplicationContext applicationContext){
        WebSocket.applicationContext = applicationContext;
    }




    /**
     * 用户id
     */
    public Integer userId;

    /**
    concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
     注：底下WebSocket是当前类名
     注：这个集合用于存放每个客户端的websocket连接
     */
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
    public void onOpen(@PathParam(value = "userId") Integer userId, Session session){
        try{
            //从redis中获取用户id和session
            this.userId =userId;
            //session会话，通过session给客户端发送数据
            webSockets.add(this);
            sessionPool.put(userId, session);
            log.info("【新的websocket连接，用户名】："+userId);
            log.info("【当前在线人数为】："+sessionPool.size());

            // 上线时查看redis中是否有离线时的消息
            //实例化Bean,读取redis中是否含有离线消息
            stringRedisTemplate = applicationContext.getBean(StringRedisTemplate.class);
            //使用redis中的Stream方法进行读取关键字为userId的Stream

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
            if(this.userId != null){
                webSockets.remove(this);
                sessionPool.remove(this.userId);
                log.info("【websocket消息：用户已断开，id】："+userId);
            }
        }catch (Exception e){
            log.error("Error 【closing】 WebSocket connection：",e);
        }
    }

    @OnError
    public void onError(Throwable error) {
        log.error("服务端发生了错误" + error.getMessage());
    }

    @OnMessage
    public void onMessage(@PathParam(value = "userId") Integer userId,String message){
        try {
            //获取当前用户id
            this.userId = userId;
            log.info("【来自客户端的消息】：{}，客户端的id：{}", message, userId);
            //将json字符串类型的数据反序列化为Message类型的对象
            JSONObject jsonObject = JSONObject.parseObject(message);
            //发送的内容
            String content = jsonObject.getString("content");
            if(content == null){
                log.info("消息不能为空");
                return;
            }
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
            message1.setContent(content);

            //实例化Bean，将信息存入数据库中
            messageMapper = applicationContext.getBean(MessageMapper.class);
            messageMapper.insert(message1);
        }catch (Exception e){
            log.error("Error 【OnMessage】:",e);
        }
    }

    public void sendMessage(String message,Integer toId) {
        try {
            //将json字符串类型的数据反序列化为Message类型的对象，再提取其中的content
            String content = JSONObject.parseObject(message).getString("content");
            if(content == null ){
                log.info("消息不能为空");
                throw new IOException("消息不能为空");
            }
            //遍历sessionPol集合，如果对方在线，那么直接发送
            // 如果对方不在线，消息存储到redis消息队列中
            Session session = sessionPool.get(toId);
            //如果对方在线，直接通过websocket发送消息
            if(session != null && session.isOpen()){
                log.info("【websocket消息】 发送消息给："+toId+" ，内容为："+message);
                session.getBasicRemote().sendText(message);
            }
            //如果对方不在线，存储到redis中的Stream消息队列中
            else{
                //实例化Bean
                stringRedisTemplate = applicationContext.getBean(StringRedisTemplate.class);
                //将message的数据进行序列化为json字符串
                String jsonMessage = JSONObject.toJSONString(message);
                // 使用XADD命令将消息存入Stream，并且以toId作为Stream的关键字
                stringRedisTemplate.opsForStream().add(toId.toString(), Collections.singletonMap("message", jsonMessage));
                log.info("【websocket】发送消息，对方不在线，待对方上线后送达消息");
            }
        } catch (Exception e) {
            log.error("Error 【sendMessage】:", e);
        }
    }
}
