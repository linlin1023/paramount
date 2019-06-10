package com.paramount.shopping.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paramount.shopping.dao.TbUserMapper;
import com.paramount.shopping.domian.*;
import com.paramount.shopping.domian.group.Goods;
import com.paramount.shopping.domian.response.PageResult;
import com.paramount.shopping.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.util.resources.pt.TimeZoneNames_pt_BR;

import java.util.List;

@Service
public class TbUserServiceImpl implements TbUserService {

    @Autowired
    TbUserMapper tbUserMapper;

    @Override
    public List<TbUser> findAll() {
        return null;
    }

    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public void add(TbUser user) {
        tbUserMapper.insert(user);
    }

    @Override
    public void update(TbUser user) {
        tbUserMapper.updateByPrimaryKey(user);
    }

    @Override
    public TbUser findOne(Long id) {
        return tbUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        for(Long id : ids){
            tbUserMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageResult findPage(TbUser user, int pageNum, int pageSize) {

            PageHelper.startPage(pageNum, pageSize);

            TbUserExample example=new TbUserExample();
            TbUserExample.Criteria criteria = example.createCriteria();

            if(user!=null){
                if(contentCheck(user.getName())){
                    criteria.andNameLike("%"+user.getName()+"%");
                }
                if(contentCheck(user.getUsername())){
                    criteria.andUsernameLike("%"+user.getUsername()+"%");
                }
                if(contentCheck(user.getEmail())){
                    criteria.andEmailLike("%"+user.getEmail()+"%");
                }
                if(contentCheck(user.getIsEmailCheck())){
                    criteria.andIsEmailCheckEqualTo(user.getIsEmailCheck());
                }
                if(contentCheck(user.getIsMobileCheck())){
                    criteria.andIsMobileCheckEqualTo(user.getIsMobileCheck());
                }
                if(contentCheck(user.getNickName())){
                    criteria.andNickNameLike("%"+user.getNickName()+"%");
                }
                if(contentCheck(user.getPhone())){
                    criteria.andPhoneLike("%"+user.getPhone()+"%");
                }
                if(contentCheck(user.getQq())){
                    criteria.andQqLike("%"+user.getQq()+"%");
                }
                if(contentCheck(user.getSex())){
                    criteria.andSexEqualTo(user.getSex());
                }
                if(contentCheck(user.getSourceType())){
                    criteria.andSourceTypeLike("%"+user.getSourceType()+"%");
                }
                if(contentCheck(user.getStatus())){
                    criteria.andStatusEqualTo(user.getStatus());
                }
                if(user.getId()!=null){
                    criteria.andIdEqualTo(user.getId());
                }

            }

            Page<TbUser> page= (Page<TbUser>)tbUserMapper.selectByExample(example);
            return new PageResult(page.getTotal(), page.getResult());
        }

    private boolean contentCheck(String content) {
        return content != null && content.length() > 0;
    }


    @Override
    public void updateStatus(Long[] ids, String status) {

    }

    @Override
    public List<TbItem> findUserListByUsersIdListAndStatus(Long[] goodsIds, String status) {
        return null;
    }
}
