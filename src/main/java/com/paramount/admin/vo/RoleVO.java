package com.paramount.admin.vo;

import com.paramount.admin.domain.Role;

import java.util.List;

/**
 */
public class RoleVO extends Role{
    private static final long serialVersionUID = 3164658000727722430L;

    private List<Long> permissionIds;

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
