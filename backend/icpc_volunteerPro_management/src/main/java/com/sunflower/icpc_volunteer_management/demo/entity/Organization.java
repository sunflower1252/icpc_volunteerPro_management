package com.sunflower.icpc_volunteer_management.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName organization
 */
@TableName(value ="organization")
@Data
public class Organization implements Serializable {
    /**
     * 组织ID
     */
    @TableId(type = IdType.AUTO)
    private Integer organizationId;

    /**
     * 组织名称
     */
    private String organizationName;

    /**
     * 组织描述
     */
    private String organizationDescription;

    /**
     * 组织类型
     */
    private Object organizationType;

    /**
     * 组织时间
     */
    private Date organizationTime;

    /**
     * 发布者id
     */
    private Integer organizationUserid;

    /**
     * 发布图片地址
     */
    private String organizationUrl;

    /**
     * 发布状态
     */
    private String organizationStatus;

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
        Organization other = (Organization) that;
        return (this.getOrganizationId() == null ? other.getOrganizationId() == null : this.getOrganizationId().equals(other.getOrganizationId()))
            && (this.getOrganizationName() == null ? other.getOrganizationName() == null : this.getOrganizationName().equals(other.getOrganizationName()))
            && (this.getOrganizationDescription() == null ? other.getOrganizationDescription() == null : this.getOrganizationDescription().equals(other.getOrganizationDescription()))
            && (this.getOrganizationType() == null ? other.getOrganizationType() == null : this.getOrganizationType().equals(other.getOrganizationType()))
            && (this.getOrganizationTime() == null ? other.getOrganizationTime() == null : this.getOrganizationTime().equals(other.getOrganizationTime()))
                && (this.getOrganizationUserid() == null ? other.getOrganizationUserid() == null : this.getOrganizationUserid().equals(other.getOrganizationUserid()))
                && (this.getOrganizationUrl() == null ? other.getOrganizationUrl() == null : this.getOrganizationUrl().equals(other.getOrganizationUrl()))
                && (this.getOrganizationStatus() == null ? other.getOrganizationStatus() == null : this.getOrganizationStatus().equals(other.getOrganizationStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrganizationId() == null) ? 0 : getOrganizationId().hashCode());
        result = prime * result + ((getOrganizationName() == null) ? 0 : getOrganizationName().hashCode());
        result = prime * result + ((getOrganizationDescription() == null) ? 0 : getOrganizationDescription().hashCode());
        result = prime * result + ((getOrganizationType() == null) ? 0 : getOrganizationType().hashCode());
        result = prime * result + ((getOrganizationTime() == null) ? 0 : getOrganizationTime().hashCode());
        result = prime * result + ((getOrganizationUserid() == null) ? 0 : getOrganizationUserid().hashCode());
        result = prime * result + ((getOrganizationUrl() == null) ? 0 : getOrganizationUrl().hashCode());
        result = prime * result + ((getOrganizationStatus() == null) ? 0 : getOrganizationStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", organizationId=").append(organizationId);
        sb.append(", organizationName=").append(organizationName);
        sb.append(", organizationDescription=").append(organizationDescription);
        sb.append(", organizationType=").append(organizationType);
        sb.append(", origanizationTime=").append(organizationTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", organizationUserId=").append(organizationUserid);
        sb.append(", organizationUrl=").append(organizationUrl);
        sb.append(", organizationStatus=").append(organizationStatus);
        sb.append("]");
        return sb.toString();
    }
}