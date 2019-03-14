/*
package com.coalvalue.domain.entity;

import com.coalvalue.domain.BaseDomain;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;

*/
/**
 * Created by Peter Xu on 01/10/2015.
 *//*

@Entity
@Table(name = "role")
@NamedNativeQueries({
@NamedNativeQuery(
        name="queryRoleUsers",
        query="select u.* from user u, role r, user_role ur where u.id = ur.user_id and r.id = ur.role_id and r.id = :roleId order by u.user_name",
        resultSetMapping="user"),
@NamedNativeQuery(
        name="queryRoleUsersCount",
        query="select count(1) as user_count from user u, role r, user_role ur where u.id = ur.user_id and r.id = ur.role_id and r.id = :roleId",
        resultSetMapping="userCount")
})
@SqlResultSetMappings({
@SqlResultSetMapping(name="user", entities = {@EntityResult(entityClass = User.class)}),
@SqlResultSetMapping(name="userCount", columns = {@ColumnResult(name = "user_count", type = Long.class)})
})
public class Role extends BaseDomain {

    @Size(min=2, max=50)
    @Column(name = "ROLE_NAME")
    private String roleName;

    @Size(max=200)
    @Column(name = "ROLE_DESC")
    private String roleDesc;

    public Role() {
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
*/
