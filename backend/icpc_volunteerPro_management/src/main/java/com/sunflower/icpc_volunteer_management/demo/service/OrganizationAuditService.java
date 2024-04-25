package com.sunflower.icpc_volunteer_management.demo.service;

import com.sunflower.icpc_volunteer_management.demo.commom.Result;
import com.sunflower.icpc_volunteer_management.demo.entity.OrganizationAudit;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author YG
* @description 针对表【organization_audit】的数据库操作Service
* @createDate 2024-04-22 16:58:32
*/
public interface OrganizationAuditService extends IService<OrganizationAudit> {

    Result deleteOrganizationAudit(Integer organizationId);
}
