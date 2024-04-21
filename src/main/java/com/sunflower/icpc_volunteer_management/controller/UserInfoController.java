package com.sunflower.icpc_volunteer_management.controller;

import com.sunflower.icpc_volunteer_management.commom.Result;
import com.sunflower.icpc_volunteer_management.service.UserInfoService;
import com.sunflower.icpc_volunteer_management.userInfo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* @author yg
*/
@RestController
@RequestMapping("/UserInfo")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    /**
     * 注册
     * @param email
     * @param password
     * @param captcha
     * @return {@link Result}
     */
    @PostMapping("/userEnroll")
    public Result userEnroll(@RequestParam String email,String password, String captcha){
        return Result.success(userInfoService.userEnroll(email,password,captcha));
    }

    /**
     * 登录
     * @param userInfo
     * @return {@link Result}
     */
    @PostMapping("/userLogin")
    public Result userLogin(@RequestBody UserInfo userInfo){
        return Result.success(userInfoService.userLogin(userInfo));
    }

}
