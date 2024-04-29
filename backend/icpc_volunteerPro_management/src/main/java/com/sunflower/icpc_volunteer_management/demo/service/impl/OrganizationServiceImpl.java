package com.sunflower.icpc_volunteer_management.demo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.sunflower.icpc_volunteer_management.demo.commom.Result;
import com.sunflower.icpc_volunteer_management.demo.mapper.OrganizationAuditMapper;
import com.sunflower.icpc_volunteer_management.demo.mapper.OrganizationMapper;
import com.sunflower.icpc_volunteer_management.demo.service.OrganizationService;
import com.sunflower.icpc_volunteer_management.demo.entity.Organization;
import com.sunflower.icpc_volunteer_management.demo.entity.OrganizationAudit;
import com.sunflower.icpc_volunteer_management.demo.mapper.UserInfoMapper;
import com.sunflower.icpc_volunteer_management.demo.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author YG
* @description 针对表【organization】的数据库操作Service实现
* @createDate 2024-04-22 13:52:12
*/
@Service
@Slf4j
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization>
    implements OrganizationService {

    @Autowired
    OrganizationMapper organizationMapper;
    @Autowired
    OrganizationAuditMapper organizationAuditMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    //引入redis
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 分页展示志愿信息
     *
     * @param type 类型
     * @param pageNum  页数
     * @param pageSize 总条数
     * @return {@link PageInfo}<{@link Organization}>
     */
    @Override
    public PageInfo<Organization> getOrganizationList(String type, int pageNum, int pageSize) {
        try{
            PageMethod.startPage(pageNum,pageSize);
            List<Organization> list = organizationMapper.getOrganizationList(type);
            return new PageInfo<>(list);
        }catch (Exception e){
            log.error(String.valueOf(e));
            return null;
        }
    }

    /**
     * 发布志愿信息
     * @param organization 要发布的信息
     * @return {@link Result}
     */
    @Override
    public Result publishOrganization(Organization organization) {
        try{
            Integer id = StpUtil.getLoginIdAsInt();
            organization.setOrganizationUserid(id);
            organization.setOrganizationStatus("未审核");
            organization.setOrganizationTime(new Date());
            log.info((String) organization.getOrganizationType());
            int insert = organizationMapper.insert(organization);
            if(insert != 1){
                return Result.error("发布申请失败01");
            }
            int organizationId = organization.getOrganizationId();
            OrganizationAudit organizationAudit = new OrganizationAudit();
            organizationAudit.setOrganizationId(organizationId);
            //根据id在UserInfo类中获取user_id等于id的userInfo
            UserInfo userInfo = userInfoMapper.selectById(id);
            if (userInfo == null) {
                throw new RuntimeException("无法找到与当前用户关联的UserInfo记录");
            }
            String username = userInfo.getName();
            organizationAudit.setOrganizationStudent(username);
            int insert1 = organizationAuditMapper.insert(organizationAudit);
            if(insert1 != 1){
                return Result.error("提交失败，请稍后重试");
            }
            return Result.success("发布申请已提交，代审核");
        }catch (Exception e){
            log.error(String.valueOf(e));
            return Result.error("发布申请失败02");
        }
    }


    /**
     * 报名志愿
     * @param organizationId 志愿服务唯一id
     * @return {@link Result}
     */
    @Override
    public Result signUpEvent(Integer organizationId){
        try{
            //点击报名，直接将两个id更新在数据库中
            Integer userId = StpUtil.getLoginIdAsInt();
            //获取报名的活动
            OrganizationAudit organizationAudit = organizationAuditMapper.selectById(organizationId);
            log.info(String.valueOf(organizationAudit));
            if(organizationAudit == null){
                return Result.error("活动不存在，请联系管理员");
            }
            //获取报名的学生
            String students = organizationAudit.getOrganizationStudent();
            StringBuilder sb = new StringBuilder();
            sb.append(students).append("，");
            //根据登录的账号，获取user_id进行名字的添加
            UserInfo userInfo = userInfoMapper.selectById(userId);
            if(userInfo == null){
                return Result.error("用户不存在，请重新登陆后报名");
            }
            Integer userid = userInfo.getUserId();
            sb.append(userid);
            students = sb.toString();
            //将数据直接存储到数据库中
            organizationAudit.setOrganizationStudent(students);
            //将id为organizationId的数据更新
            int update = organizationAuditMapper.updateById(organizationAudit);
            if(update != 1){
                return Result.error("报名失败");
            }
            return Result.success("报名成功");
        }catch (Exception e){
            log.error(String.valueOf(e));
            return Result.error("报名失败");
        }
    }

    /**
     * 获取活动的详细信息
     * @param organizationId 活动id
     * @return {@link Result}
     */
    @Override
    public Result getOrganization(Integer organizationId){
        try{
            //将点击的数据以List集合形式返回
            Organization organization = organizationMapper.selectById(organizationId);
            if(organization == null){
                return Result.error("活动不存在，请联系管理员");
            }
            return Result.success(organization);
        }catch (Exception e){
            return Result.error("服务器响应失败，请联系管理员解决");
        }
    }

    /**
     * 编辑活动信息
     * @param organization 修改好的数据
     * @return {@link Result}
     */
    @Override
    public Result reviseOrganization(Organization organization){
        try{
            //将此活动从未审核改为审核通过
            if("审核通过".equals(organization.getOrganizationStatus())){
                organization.setOrganizationType("未审核");
            }
            //更新organization数据库
            int update1 = organizationMapper.updateById(organization);
            if(update1 != 1){
                return Result.error("修改失败01");
            }
            //在organizationAudit表中获取报名的userid，
            //根据获取的userid在userInfo表中获取email邮箱地址
            // 然后进行一个一个发邮箱进行重新报名的邮件
            OrganizationAudit organizationAudit = organizationAuditMapper.selectById(organization.getOrganizationId());
            String students = organizationAudit.getOrganizationStudent();
            String[] split = students.split(",");
            String name = organization.getOrganizationName();
            int length = split.length;
            for (String student : split) {
                UserInfo userInfo = userInfoMapper.selectById(student);
                String email = userInfo.getEmail();
                String text = "活动更新通知";
                String word = "您好，您报名的" + "“" + name + "”" + "活动已经更新，请稍稍后重新报名，在这里给您添麻烦了，不好意思";
                MailUtil.send(email, text, word, false);
            }
            //更新organizationAudit数据库，删除学生姓名
            organizationAudit.setOrganizationStudent("");
            int update2 = organizationAuditMapper.updateById(organizationAudit);
            if(update2 != 1){
                return Result.error("修改失败02");
            }
            return Result.success("修改成功");
        }catch (Exception e){
            log.error(String.valueOf(e));
            return Result.error("服务器响应错误，请联系管理员");
        }
    }
}
