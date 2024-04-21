package com.sunflower.icpc_volunteer_management.controller;

import com.sunflower.icpc_volunteer_management.commom.Result;
import com.sunflower.icpc_volunteer_management.service.UserInfoService;
import com.sunflower.icpc_volunteer_management.userInfo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yg
 */
@RestController
@RequestMapping("/UserInfo")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    /**
     * @param email    邮箱
     * @param password 密码
     * @param captcha  验证码
     * @return {@link Result}
     */
    @PostMapping("/userEnroll")
    public Result userEnroll(@RequestParam String email, String password, String captcha) {
        return Result.success(userInfoService.userEnroll(email, password, captcha));
    }

    /**
     * 登录
     *
     * @param userInfo 用户信息
     * @return {@link Result}
     */
    @PostMapping("/userLogin")
    public Result userLogin(@RequestBody UserInfo userInfo) {
        return Result.success(userInfoService.userLogin(userInfo));
    }

    /**
     * 上传个人照片
     *
     * @param file 头像地址
     * @return {@link Result}
     */
    @PostMapping("/uploadProfilePicture")
    public Result uploadProfilePicture(MultipartFile file) {
        return Result.success(userInfoService.uploadProfilePicture(file));
    }


    /**
     * 显示个人资料
     *
     * @return {@link Result}
     */
    @PostMapping("/editProfile")
    public Result editProfile() {
        return Result.success(userInfoService.editProfile());
    }


    /**
     * 更新个人信息
     *
     * @param userInfo 已更改的对象
     * @return {@link Result}
     */
    @PostMapping("/changeProfile")
    public Result changeProfile(@RequestBody UserInfo userInfo) {
        return Result.success(userInfoService.changeProfile(userInfo));
    }
}
