package com.sunflower.icpc_volunteer_management.userOrganizationInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @author yg
 * @TableName user_organization_info
 */
@TableName(value ="user_organization_info")
@Data
public class UserOrganizationInfo implements Serializable {
    /**
     * 用户组织ID
     */
    @TableId(type = IdType.AUTO)
    private Integer userOrganizationId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 组织ID
     */
    private Integer organizationId;

    /**
     * 成员职称
     */
    private String memberTitle;

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
        UserOrganizationInfo other = (UserOrganizationInfo) that;
        return (this.getUserOrganizationId() == null ? other.getUserOrganizationId() == null : this.getUserOrganizationId().equals(other.getUserOrganizationId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getOrganizationId() == null ? other.getOrganizationId() == null : this.getOrganizationId().equals(other.getOrganizationId()))
            && (this.getMemberTitle() == null ? other.getMemberTitle() == null : this.getMemberTitle().equals(other.getMemberTitle()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserOrganizationId() == null) ? 0 : getUserOrganizationId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOrganizationId() == null) ? 0 : getOrganizationId().hashCode());
        result = prime * result + ((getMemberTitle() == null) ? 0 : getMemberTitle().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userOrganizationId=").append(userOrganizationId);
        sb.append(", userId=").append(userId);
        sb.append(", organizationId=").append(organizationId);
        sb.append(", memberTitle=").append(memberTitle);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}