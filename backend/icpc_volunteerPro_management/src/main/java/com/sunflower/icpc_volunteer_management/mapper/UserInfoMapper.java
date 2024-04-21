package com.sunflower.icpc_volunteer_management.mapper;

import com.sunflower.icpc_volunteer_management.userInfo.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yg
* @description 针对表【user_info】的数据库操作Mapper
* @createDate 2024-04-19 00:47:45
* @Entity com.sunflower.icpc_volunteer_management.userInfo.UserInfo
*/
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}




