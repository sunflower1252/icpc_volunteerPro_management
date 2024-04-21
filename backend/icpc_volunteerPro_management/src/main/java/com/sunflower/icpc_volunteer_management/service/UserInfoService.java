package com.sunflower.icpc_volunteer_management.service;

import com.sunflower.icpc_volunteer_management.commom.Result;
import com.sunflower.icpc_volunteer_management.userInfo.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yg
 * @description 针对表【user_info】的数据库操作Service
 * @createDate 2024-04-19 00:47:45
 */
public interface UserInfoService extends IService<UserInfo> {
    //注册
    Result userEnroll(String email,String password, String captcha);

    //登录
    Result userLogin(UserInfo userInfo);

    //上传个人照片
    Result uploadProfilePicture(MultipartFile file);

    //显示个人资料
    Result editProfile();

    //更新个人资料
    Result changeProfile(UserInfo userInfo);
}
