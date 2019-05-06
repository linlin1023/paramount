package com.paramount.shopping.service.impl;
import java.util.List;
import java.util.stream.Collectors;

import com.paramount.shopping.dao.TbItemCatMapper;
import com.paramount.shopping.domian.TbItemCat;
import com.paramount.shopping.domian.TbItemCatExample;
import com.paramount.shopping.domian.response.PageResult;
import com.paramount.shopping.service.ItemCatService;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;


	/**
	 * 查询全部
	 */
	@Override
	public List<TbItemCat> findAll() {

		List <TbItemCat> list  = (List<TbItemCat>) redisTemplate.boundHashOps("cat").get("catAll");
		if(list ==  null){
			System.out.println("从缓存里面寻找分类数据，");
			list = itemCatMapper.selectByExample(null);
			System.out.println("把所有分类数据存储到redis");
			redisTemplate.boundHashOps("cat").put("catAll", list);
		}else{
			System.out.println("从缓存里面查看所有分类数据");
		}
		return list;
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbItemCat> page=   (Page<TbItemCat>) itemCatMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbItemCat itemCat) {
		itemCatMapper.insert(itemCat);
		redisTemplate.boundHashOps("cat").delete("catAll");
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbItemCat itemCat){
		itemCatMapper.updateByPrimaryKey(itemCat);
		redisTemplate.boundHashOps("cat").delete("catAll");
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbItemCat findOne(Long id){
		return itemCatMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 * 对于子分类级联删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			itemCatMapper.deleteByPrimaryKey(id);
			List<TbItemCat> childrenList = findByParentId(id);
			Long[] idLists = childrenList.stream().map(a->a.getId()).toArray(size->new Long[size]);
			delete(idLists);

		}		
	}


	
		@Override
	public PageResult findPage(TbItemCat itemCat, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbItemCatExample example=new TbItemCatExample();
		TbItemCatExample.Criteria criteria = example.createCriteria();
		
		if(itemCat!=null){			
						if(itemCat.getName()!=null && itemCat.getName().length()>0){
				criteria.andNameLike("%"+itemCat.getName()+"%");
			}
	
		}
		
		Page<TbItemCat> page= (Page<TbItemCat>)itemCatMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
		
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public List<TbItemCat> findByParentId(Long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		TbItemCatExample.Criteria criteria = example.createCriteria();
		// 设置条件:
		criteria.andParentIdEqualTo(parentId);
		// 条件查询
		
		//将模板ID放入缓存（以商品分类名称作为key）	
		
		List<TbItemCat> itemCatList = findAll();
		for(TbItemCat itemCat:itemCatList){
			redisTemplate.boundHashOps("itemCat").put(itemCat.getName(), itemCat.getTypeId());
		}
		System.out.println("将模板ID放入缓存");
		
		return itemCatMapper.selectByExample(example);
	}
	
}
