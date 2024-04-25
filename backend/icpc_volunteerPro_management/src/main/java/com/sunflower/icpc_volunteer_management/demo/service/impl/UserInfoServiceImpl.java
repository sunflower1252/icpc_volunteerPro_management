package com.sunflower.icpc_volunteer_management.demo.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunflower.icpc_volunteer_management.demo.commom.Result;
import com.sunflower.icpc_volunteer_management.demo.mapper.UserInfoMapper;
import com.sunflower.icpc_volunteer_management.demo.service.UserInfoService;
import com.sunflower.icpc_volunteer_management.demo.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    //引入云平台存储
    @Autowired
    private FileStorageService fileStorageService;


    /**
     * 注册
     *
     * @param email    邮箱
     * @param password 密码
     * @param captcha  验证码
     * @return {@link Result}
     */
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

    /**
     * 登录
     *
     * @param userInfo 用户信息
     * @return {@link Result}
     */
    @Override
    public Result userLogin(UserInfo userInfo) {
        try {
            String email = userInfo.getEmail();
            String password = userInfo.getPassword();
            if (email == null) {
                return Result.error("此项为必填项，请输入邮箱");
            }
            //进行邮箱的验证
            UserInfo userInfo1 = query().eq("email", email).one();
            //账号不存在
            if (userInfo1 == null) {
                return Result.error("账户未注册，请注册后登录");
            }
            //比对账号和密码
            if (password == null) {
                return Result.error("此项为必填项，请输入密码");
            }
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
            return Result.error("未知错误，请重新尝试");
        }
    }

    /**
     * 上传用户头像
     *
     * @return 返回操作结果，成功则返回成功消息，失败则返回错误消息
     */
    @Override
    public Result uploadProfilePicture(MultipartFile file) {
        try {
            Integer id = StpUtil.getLoginIdAsInt();
            UserInfo userInfo = query().eq("user_id", id).one();
            if (userInfo == null) {
                return Result.error("用户不存在");
            }
            //上传头像到云平台
            FileInfo fileInfo = fileStorageService.of(file)
                    .setObjectId(id)   //关联对象id
                    .upload();  //将文件上传到云平台
            //获取头像上传后的url地址，存储到数据库中
            String profilePicture = fileInfo.getUrl();
            userInfo.setProfilePicture(profilePicture);
            boolean exists = fileStorageService.exists(fileInfo);
            int update = userInfoMapper.updateById(userInfo);
            if (exists && update == 1) {
                return Result.success("上传成功");
            } else {
                return Result.error("上传失败");
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return Result.error("未知错误，请重新尝试");
        }
    }

    /**
     * 显示个人资料
     *
     * @return {@link Result}
     */
    @Override
    public Result editProfile() {
        try {
            Integer id = StpUtil.getLoginIdAsInt();
            UserInfo userInfo1 = query().eq("user_id", id).one();
            if (userInfo1 == null) {
                return Result.error("用户不存在,请退出重新登录");
            }
            UserInfo userInfo2 = new UserInfo();
            userInfo2.setName(userInfo1.getName());
            userInfo2.setSchool(userInfo1.getSchool());
            userInfo2.setGrade(userInfo1.getGrade());
            userInfo2.setAddress(userInfo1.getAddress());
            userInfo2.setEmail(userInfo1.getEmail());
            userInfo2.setPhone(userInfo1.getPhone());
            userInfo2.setInterest(userInfo1.getInterest());
            userInfo2.setSkill(userInfo1.getSkill());
            return Result.success(userInfo2);
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return Result.error("未知错误，请重新尝试");
        }
    }


    /**
     * 更新个人信息
     *
     * @param userInfo 改后的个人信息
     * @return {@link Result}
     */
    public Result changeProfile(UserInfo userInfo) {
        try {
            Integer id = StpUtil.getLoginIdAsInt();
            if (id == null) {
                return Result.error("用户不存在");
            }
            UserInfo userInfo1 = query().eq("user_id", id).one();
            if (userInfo1 == userInfo) {
                return Result.error("未进行修改");
            }
            UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", id);
            int result = userInfoMapper.update(userInfo, updateWrapper);
            if (result == 1) {
                return Result.success("修改成功");
            } else {
                return Result.error("未找到指定用户");
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return Result.error("未知错误，请重新尝试");
        }
    }

}




