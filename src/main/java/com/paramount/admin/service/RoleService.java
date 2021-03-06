package com.paramount.admin.service;

import com.paramount.admin.domain.Role;
import com.paramount.admin.vo.RoleVO;

import java.util.List;
import java.util.Map;

/**
 * Created by szz on 2018/3/27 12:15.
 * Email szhz186@gmail.com
 */
public interface RoleService {
    int count(Map<String, Object> params);

    List<Role> list(Map<String, Object> params, Integer offset, Integer limit);

    Role getById(Long id);

    List<Role> listByUserId(Long userId);

    void delete(Long id);

    void saveRole(RoleVO roleVO);

    void deleteRole(Long id);
}
