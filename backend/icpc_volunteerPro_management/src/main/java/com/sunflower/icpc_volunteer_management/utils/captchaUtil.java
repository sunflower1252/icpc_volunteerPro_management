package com.sunflower.icpc_volunteer_management.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author yg
 */
@Slf4j
@Component
public class captchaUtil {

    //引入redis
    @Autowired
    static StringRedisTemplate stringRedisTemplate;


    //生成验证码
    public String sendCaptcha(int num){
        StringBuilder sb = new StringBuilder();
        sb.append("您正在进行注册，注册所需要的验证码为：");
        String captcha = createCaptcha(num);
        sb.append(captcha);
        sb.append("。验证码5分钟内有效，请勿泄露和转发。如非本人操作，请忽略此邮件");
        return sb.toString();
    }

    //生成n为验证码
    public static String createCaptcha(int num){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        String[] arr = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for(int i =0;i<num;i++){
            int index = random.nextInt(arr.length);
            sb.append(arr[index]);
        }
        return sb.toString();
    }

}
