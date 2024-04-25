package com.sunflower.icpc_volunteer_management.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName organization_audit
 */
@TableName(value ="organization_audit")
@Data
public class OrganizationAudit implements Serializable {
    /**
     * 志愿服务id
     */
    @TableId
    private Integer organizationId;

    /**
     * 参加的学生
     */
    private String organizationStudent;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OrganizationAudit other = (OrganizationAudit) that;
        return (this.getOrganizationId() == null ? other.getOrganizationId() == null : this.getOrganizationId().equals(other.getOrganizationId()))
            && (this.getOrganizationStudent() == null ? other.getOrganizationStudent() == null : this.getOrganizationStudent().equals(other.getOrganizationStudent()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrganizationId() == null) ? 0 : getOrganizationId().hashCode());
        result = prime * result + ((getOrganizationStudent() == null) ? 0 : getOrganizationStudent().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", organizationInfo=").append(organizationId);
        sb.append(", organizationStudent=").append(organizationStudent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}