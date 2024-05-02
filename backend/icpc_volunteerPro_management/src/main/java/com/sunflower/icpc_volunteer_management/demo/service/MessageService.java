package com.sunflower.icpc_volunteer_management.demo.service;

import com.sunflower.icpc_volunteer_management.demo.commom.Result;
import com.sunflower.icpc_volunteer_management.demo.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author YG
* @description 针对表【message】的数据库操作Service
* @createDate 2024-04-29 16:09:11
*/
public interface MessageService extends IService<Message> {

    //获取会话列表
    Result getLetterList();

    Result readLetter(Integer toId);
}
