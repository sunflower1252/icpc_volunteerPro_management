package com.sunflower.icpc_volunteer_management.demo.mapper;

import com.sunflower.icpc_volunteer_management.demo.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
* @author YG
* @description 针对表【message】的数据库操作Mapper
* @createDate 2024-04-29 16:09:11
* @Entity com.sunflower.icpc_volunteer_management.demo.entity.Message
*/
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    List<String> selectByLetter(Integer userId);

}




