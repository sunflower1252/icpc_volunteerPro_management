package com.sunflower.icpc_volunteer_management.controller;

import cn.hutool.extra.mail.MailUtil;
import com.sunflower.icpc_volunteer_management.commom.Result;
import com.sunflower.icpc_volunteer_management.utils.captchaUtil;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author yg
 */
@Controller
@RestController("captcha")
@Slf4j
public class captchaController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //注册
    @GetMapping("/sendMessage")
    public Result sendMessage(@RequestParam @Email String email){
        try{
            //生成验证码
            String captcha = captchaUtil.createCaptcha(6);
            MailUtil.send(email,"验证码",captcha,false);
            //将验证码存入redis中，设置5分钟失效
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(email, captcha,5, TimeUnit.MINUTES);
            return Result.success(captcha);
        }catch (Exception e){
            log.error(String.valueOf(e));
            return Result.error("发送失败");
        }
    }
}
