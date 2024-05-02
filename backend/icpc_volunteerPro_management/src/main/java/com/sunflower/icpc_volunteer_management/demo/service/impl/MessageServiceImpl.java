package com.sunflower.icpc_volunteer_management.demo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunflower.icpc_volunteer_management.demo.commom.Result;
import com.sunflower.icpc_volunteer_management.demo.entity.Message;
import com.sunflower.icpc_volunteer_management.demo.entity.UserInfo;
import com.sunflower.icpc_volunteer_management.demo.mapper.UserInfoMapper;
import com.sunflower.icpc_volunteer_management.demo.service.MessageService;
import com.sunflower.icpc_volunteer_management.demo.mapper.MessageMapper;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import java.net.http.WebSocket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author YG
 * @description 针对表【message】的数据库操作Service实现
 * @createDate 2024-04-29 16:09:11
 */
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    MessageMapper messageMapper;

    /**
     * 展示会话列表
     *
     * @return 通用返回结果，服务端响应的数据最终都会封装成此对象
     */
    @Override
    public Result getLetterList() {
        try {
            Integer userId = StpUtil.getLoginIdAsInt();
            //获取所有不重复的会话id
            List<String> conlist = messageMapper.selectByLetter(userId);
            List<Map<String, Object>> conversations = new ArrayList<>();
            if (conlist.isEmpty()) {
                return null;
            }
            Long count = 0L;
            for (String el : conlist) {
                Map<String, Object> map = new HashMap<>();
                if (messageMapper.selectCount(new QueryWrapper<Message>().eq("conversation_id", el)) > 1) {
                    List<Message> messages = messageMapper.selectList(new QueryWrapper<Message>().eq("conversation_id", el).orderByDesc("create_time"));
                    //向map集合中添加会话
                    map.put("conversation", messages.get(0));
                    //对面会话id的头像以及名字
                    //判断当前用户是fromId还是toId
                    int toId;
                    if (Objects.equals(messages.get(0).getToId(), userId)) {
                        toId = messages.get(0).getFromId();
                    } else {
                        toId = messages.get(0).getToId();
                    }
                    UserInfo userInfo = userInfoMapper.selectById(toId);
                    if (userInfo == null) {
                        return Result.error("未找到指定用户，该用户已注销");
                    }
                    String name = userInfo.getName();
                    map.put("name", name);
                    String url = userInfo.getProfilePicture();
                    map.put("picture", url);
                    //还需添加本人对此会话的未读消息数量
                    Long unreadLetterCount = messageMapper.selectCount(new QueryWrapper<Message>()
                            .eq("conversation_id", el)
                            .eq("to_id", userId)
                            .eq("from_id", toId)
                            .eq("status", 0)
                    );
                    //单独未读数量
                    count += unreadLetterCount;
                    map.put("unreadLetterCount", unreadLetterCount);

                    conversations.add(map);
                } else {
                    Message messages = messageMapper.selectOne(new QueryWrapper<Message>().eq("conversation_id", el));
                    map.put("conversation", messages);

                    //对面会话id的头像以及名字
                    int toId;
                    if (Objects.equals(messages.getToId(), userId)) {
                        toId = messages.getFromId();
                    } else {
                        toId = messages.getToId();
                    }
                    UserInfo userInfo = userInfoMapper.selectById(toId);
                    if (userInfo == null) {
                        return Result.error("未找到指定用户，该用户已注销");
                    }
                    String name = userInfo.getName();
                    map.put("name", name);
                    String url = userInfo.getProfilePicture();
                    map.put("picture", url);

                    //还需添加本人对此会话的未读消息数量
                    Long unreadLetterCount = messageMapper.selectCount(new QueryWrapper<Message>()
                            .eq("conversation_id", el)
                            .eq("to_id", userId)
                            .eq("from_id", toId)
                            .eq("status", 0)
                    );
                    //单独未读数量
                    count += unreadLetterCount;
                    map.put("unreadLetterCount", unreadLetterCount);

                    conversations.add(map);
                }
            }
            //所有会话加起来的未读消息总数
            //总未读数量
            Map<String, Object> map = new HashMap<>();
            map.put("unreadCount", count);
            conversations.add(map);
            return Result.success(conversations);
        } catch (Exception e) {
            log.error("【Error show LetterList :】："+e.getMessage());
            return Result.error("服务器响应错误，请联系管理员");
        }
    }

    /**
     * 未读变已读
     * @param toId 会话对象id
     * @return 通用返回结果，服务端响应的数据最终都会封装成此对象
     */
    @Override
    public Result readLetter(Integer toId) {
        //1.获取用户对话私信列表
        //2.判断对话中的私信是否为未读状态
        //3.将所有未读消息更改为已读状态
        int fromId = StpUtil.getLoginIdAsInt();
        String conversationId;
        if (fromId < toId) {
            conversationId = fromId + "_" + toId;
        } else {
            conversationId = toId + "_" + fromId;
        }
        //更新message表
        messageMapper.update(null, new UpdateWrapper<Message>()
                .set("status", 1)
                .eq("from_id", toId)
                .eq("conversation_id", conversationId)
        );
        return Result.success("消息已读");
    }
}




