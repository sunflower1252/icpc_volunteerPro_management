package com.sunflower.icpc_volunteer_management.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunflower.icpc_volunteer_management.demo.commom.Result;
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


    /**
     * 删除活动
     * @param organizationId 要被删除对的活动id
     * @return {@link Result}
     */
    @Override
    public Result deleteOrganizationAudit(Integer organizationId){
        try{
            //删除活动
            int delete = organizationAuditMapper.deleteById(organizationId);
            if(delete != 1){
                return Result.error("删除失败，此活动不存在");
            }
            return Result.success("删除成功");
        }catch (Exception e){
            return Result.error("服务器响应错误，请联系管理员");
        }
    }

}




