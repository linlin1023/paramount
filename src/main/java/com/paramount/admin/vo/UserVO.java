package com.paramount.admin.vo;

import com.paramount.admin.domain.User;

import java.util.List;

/**
 */
public class UserVO extends User{

    private static final long serialVersionUID = 4613848879669316299L;
    private List<Long> roleIds;

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
