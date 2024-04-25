package com.sunflower.icpc_volunteer_management.demo.controller;

import com.sunflower.icpc_volunteer_management.demo.commom.Result;
import com.sunflower.icpc_volunteer_management.demo.service.OrganizationAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YG
 */
@Controller
@RestController("/OrganizationAudit")
public class OrganizationAuditController {

    @Autowired
    OrganizationAuditService organizationAuditService;

    /**
     * 删除活动
     * @param organizationId 要被删除的活动id
     * @return {@link Result}
     */
    @GetMapping("/deleteOrganizationAudit")
    public Result deleteOrganizationAudit(Integer organizationId) {
        return organizationAuditService.deleteOrganizationAudit(organizationId);
    }
}