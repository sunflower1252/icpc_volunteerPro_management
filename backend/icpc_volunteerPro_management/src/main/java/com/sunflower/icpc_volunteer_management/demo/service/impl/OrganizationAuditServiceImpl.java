package com.sunflower.icpc_volunteer_management.demo.service.impl;

import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunflower.icpc_volunteer_management.demo.commom.Result;
import com.sunflower.icpc_volunteer_management.demo.entity.Organization;
import com.sunflower.icpc_volunteer_management.demo.entity.UserInfo;
import com.sunflower.icpc_volunteer_management.demo.mapper.OrganizationMapper;
import com.sunflower.icpc_volunteer_management.demo.mapper.UserInfoMapper;
import com.sunflower.icpc_volunteer_management.demo.service.OrganizationAuditService;
import com.sunflower.icpc_volunteer_management.demo.entity.OrganizationAudit;
import com.sunflower.icpc_volunteer_management.demo.mapper.OrganizationAuditMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author YG
* @description 针对表【organization_audit】的数据库操作Service实现
* @createDate 2024-04-22 16:58:32
*/
@Service
@Slf4j
public class OrganizationAuditServiceImpl extends ServiceImpl<OrganizationAuditMapper, OrganizationAudit>
    implements OrganizationAuditService {

    @Autowired
    OrganizationAuditMapper organizationAuditMapper;
    @Autowired
    OrganizationMapper organizationMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 删除活动
     * @param organizationId 要被删除对的活动id
     * @return {@link Result}
     */
    @Override
    public Result deleteOrganizationAudit(Integer organizationId){
        try{
            //删除活动
            //获取organizationAudit表中关于这个活动的报名学生userid，通过userid在userInfo表中获取email，再给学生发邮件通知活动已取消
            OrganizationAudit organizationAudit = organizationAuditMapper.selectById(organizationId);
            //在organizationAudit表中获取所有参加活动的学生的userId
            String students = organizationAudit.getOrganizationStudent();
            //通过organizationId在organization表中获取活动名称
            String organizationName = organizationMapper.selectById(organizationId).getOrganizationName();
            String[] split = students.split("，");
            int length = split.length;
            for (String student : split) {
                UserInfo userInfo = userInfoMapper.selectById(student);
                String email = userInfo.getEmail();
                String text = "活动删除通知";
                String word = "您好，您报名的" + "“" + organizationName + "”" + "活动已经被删除，请注意活动的最新消息";
                MailUtil.send(email, text, word, false);
            }
            int delete = organizationAuditMapper.deleteById(organizationId);
            if(delete != 1){
                return Result.error("删除失败，此活动不存在");
            }
            return Result.success("删除成功");
        }catch (Exception e){
            log.error(String.valueOf(e));
            return Result.error("服务器响应错误，请联系管理员");
        }
    }

    /**
     * 获取活动的所有信息
     * @param organizationId 活动的id
     * @return {@link Result}
     */
    @Override
    public Result getStudentsFromOrganizationAudit(Integer organizationId) {
        try{
            OrganizationAudit organizationAudit = organizationAuditMapper.selectById(organizationId);
            if(organizationAudit == null){
                return Result.error("此活动不存在");
            }
            return Result.success(organizationAudit);
        }catch (Exception e){
            log.error(String.valueOf(e));
            return Result.error("服务器响应错误，请稍后重试");
        }
    }

}

