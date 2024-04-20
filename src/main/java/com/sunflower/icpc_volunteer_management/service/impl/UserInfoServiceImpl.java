package com.sunflower.icpc_volunteer_management.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunflower.icpc_volunteer_management.commom.Result;
import com.sunflower.icpc_volunteer_management.mapper.UserInfoMapper;
import com.sunflower.icpc_volunteer_management.service.UserInfoService;
import com.sunflower.icpc_volunteer_management.userInfo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author yg
 * @description 针对表【user_info】的数据库操作Service实现
 * @createDate 2024-04-19 00:47:45
 */
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {
    //引入redis
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserInfoMapper userInfoMapper;

    //注册
    @Override
    public Result userEnroll(String email, String password, String captcha) {
        //检查数据库
        UserInfo userInfo1 = query().eq("email", email).one();
        //用户存在,返回
        if (userInfo1 != null) {
            return Result.error("账户已存在，请重新注册");
        }
        //注册
        try {
            UserInfo userInfo = new UserInfo();
            //设置用户的基本信息
            userInfo.setEmail(email);
            userInfo.setRole("user");
            userInfo.setPassword(DigestUtil.sha1Hex(password));
            //用户点击“发送验证码”按钮后，验证码在redis中
            //从redis中获取key为email的value值进行比较
            String captchaFromRedis = stringRedisTemplate.opsForValue().get(email);
            //验证码通过
            if (captchaFromRedis != null && captchaFromRedis.equals(captcha)) {
                int insert = userInfoMapper.insert(userInfo);
                //添加成功
                if (insert == 1) {
                    return Result.success("注册成功");
                } else {
                    return Result.error("添加用户功能错误，请重新注册");
                }
            }
            //验证码不通过
            else {
                return Result.error("验证码错误，请重新输入正确的验证码");
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return Result.error("注册方法错误");
        }
    }

    //登录
    @Override
    public Result userLogin(UserInfo userInfo) {
        try {
            String email = userInfo.getEmail();
            String password = userInfo.getPassword();
            //进行邮箱的验证
            UserInfo userInfo1 = query().eq("email", email).one();
            //账号不存在
            if (userInfo1 == null) {
                Result.error("账户未注册，请注册后登录");
            }
            //比对账号和密码
            password = DigestUtil.sha1Hex(password);
            if (!userInfo1.getPassword().equals(password)) {
                return Result.error("密码错误，请重新输入");
            }
            //返回token给前端，id签名
            Integer id = userInfo1.getUserId();
            StpUtil.login(id);
            SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
            return Result.success(saTokenInfo);
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return Result.error("服务器错误，请重新尝试");
        }
    }


}




