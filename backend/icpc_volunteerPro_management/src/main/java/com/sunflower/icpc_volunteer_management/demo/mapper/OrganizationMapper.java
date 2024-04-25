package com.sunflower.icpc_volunteer_management.demo.mapper;

import com.sunflower.icpc_volunteer_management.demo.entity.Organization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author YG
* @description 针对表【organization】的数据库操作Mapper
* @createDate 2024-04-22 13:52:12
* @Entity com.sunflower.icpc_volunteer_management.userInfo.Organization
*/

@Mapper
public interface OrganizationMapper extends BaseMapper<Organization> {

    List<Organization> getOrganizationList(String type);
}




