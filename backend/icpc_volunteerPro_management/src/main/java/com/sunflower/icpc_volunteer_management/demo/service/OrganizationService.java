package com.sunflower.icpc_volunteer_management.demo.service;

import com.github.pagehelper.PageInfo;
import com.sunflower.icpc_volunteer_management.demo.commom.Result;
import com.sunflower.icpc_volunteer_management.demo.entity.Organization;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author YG
* @description 针对表【organization】的数据库操作Service
* @createDate 2024-04-22 13:52:12
*/
public interface OrganizationService extends IService<Organization> {

    PageInfo<Organization> getOrganizationList(String type, int pageNum, int pageSize);

    Result publishOrganization(Organization organization);

    Result signUpEvent(Integer organizationId);

    Result getOrganization(Integer organizationId);

    Result reviseOrganization(Organization organization);
}
