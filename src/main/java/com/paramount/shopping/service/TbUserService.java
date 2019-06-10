package com.paramount.shopping.service;

import com.paramount.shopping.domian.TbItem;
import com.paramount.shopping.domian.TbUser;
import com.paramount.shopping.domian.response.PageResult;

import java.util.List;

public interface TbUserService {

    public List<TbUser> findAll();

    public PageResult findPage(int pageNum, int pageSize);

    public void add(TbUser user);

    public void update(TbUser user);

    public TbUser findOne(Long id);

    public void delete(Long[] ids);

    public PageResult findPage(TbUser user, int pageNum, int pageSize);

    public void updateStatus(Long[] ids, String status);

    public List<TbItem>	findUserListByUsersIdListAndStatus(Long[] usersIds, String status);
}
