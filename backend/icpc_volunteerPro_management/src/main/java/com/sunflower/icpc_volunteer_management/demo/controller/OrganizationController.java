package com.sunflower.icpc_volunteer_management.demo.controller;

import com.sunflower.icpc_volunteer_management.demo.commom.Result;
import com.sunflower.icpc_volunteer_management.demo.service.OrganizationService;
import com.sunflower.icpc_volunteer_management.demo.entity.Organization;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author YG
 */
@RestController("/Organization")
@Controller
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    /**
     * 分页展示志愿信息
     * @param pageNum 页数
     * @param pageSize 总条数
     * @return {@link Result}
     */
    @PostMapping("/getOrganization/{pageNum}/{pageSize}")
    public Result getOrganizationList(@RequestParam(required = false) String type, @PathVariable int pageNum, @PathVariable int pageSize) {
        return Result.success(organizationService.getOrganizationList(type,pageNum,pageSize));
    }

    /**
     * 发布志愿信息
     * @param organization 要发布的信息
     * @return {@link Result}
     */
    @PostMapping("/publishOrganization")
    public Result publishOrganization(@RequestBody @NotNull Organization organization) {
        return Result.success(organizationService.publishOrganization(organization));
    }


    /**
     * 报名志愿
     * @param organizationId 志愿服务唯一id
     * @return {@link Result}
     */
    @PostMapping("/signUpEvent")
    public Result signUpEvent(@RequestParam Integer organizationId) {
        return Result.success(organizationService.signUpEvent(organizationId));
    }

    /**
     * 获取活动详细信息
     * @param organizationId 活动id
     * @return {@link Result}
     */
    @PostMapping("/getOrganization")
    public Result getOrganization(@RequestParam Integer organizationId) {
        return Result.success(organizationService.getOrganization(organizationId));
    }


    /**
     * 编辑活动信息
     * @param organization 要修改的信息
     * @return {@link Result}
     */
    @PostMapping("/reviseOrganization")
    public Result reviseOrganization(@RequestBody @NotNull Organization organization) {
        return Result.success(organizationService.reviseOrganization(organization));
    }


}
