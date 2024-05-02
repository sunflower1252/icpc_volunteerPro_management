package com.sunflower.icpc_volunteer_management.demo.controller;

import com.sunflower.icpc_volunteer_management.demo.commom.Result;
import com.sunflower.icpc_volunteer_management.demo.entity.Message;
import com.sunflower.icpc_volunteer_management.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    /**
     * 展示会话列表
     * @return 通用返回结果，服务端响应的数据最终都会封装成此对象
     */
    @PostMapping("/getLetterList")
    public Result getLetterList(){
        return Result.success(messageService.getLetterList());
    }

    /**
     * 未读变已读
     * @param toId 会话对象id
     * @return 通用返回结果，服务端响应的数据最终都会封装成此对象
     */
    @PostMapping("/readLetter")
    public Result readLetter(@RequestParam Integer toId){
        return Result.success(messageService.readLetter(toId));
    }
}
