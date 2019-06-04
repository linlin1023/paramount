package com.paramount.shopping.service.impl;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.paramount.shopping.dao.TbAddressMapper;
import com.paramount.shopping.dao.TbGoodsDescMapper;
import com.paramount.shopping.dao.TbGoodsMapper;
import com.paramount.shopping.dao.TbItemMapper;
import com.paramount.shopping.domian.TbGoodsDesc;
import com.paramount.shopping.domian.TbItem;
import com.paramount.shopping.domian.TbItemExample;
import com.paramount.shopping.domian.response.PageResult;
import com.paramount.shopping.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbItem> findAll() {
		return itemMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbItem> page=   (Page<TbItem>) itemMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}



	/**
	 * 增加
	 */
	@Override
	public void add(TbItem item) {
		itemMapper.insert(item);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbItem item){
		itemMapper.updateByPrimaryKey(item);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbItem findOne(Long id){
		TbItem item = itemMapper.selectByPrimaryKey(id);

		TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(item.getGoodsId());
		item.setGoodsDesc(tbGoodsDesc);
		return item;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			itemMapper.deleteByPrimaryKey(id);
		}		
	}

	@Override
	public PageResult findPageByText( String keywords, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<String> keywordsToken = tokenizeForKeywords(keywords);

		TbItemExample example=new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();


		if(keywordsToken != null && keywordsToken.size() > 0){
			for(String keyword : keywordsToken){
				//keyword
				criteria.andTitleLike("%"+keyword+"%");
				example.or(example.createCriteria().andCategoryLike("%"+keyword+"%"));
				example.or(example.createCriteria().andBrandLike("%"+keyword+"%"));
			}
		}
		Page<TbItem> page= (Page<TbItem>)itemMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}

	private List<String> tokenizeForKeywords(String keywords) {
		List<String> keywordsTokens = Lists.newArrayList(keywords.split("\\W+"));
		List<Integer> indexes = new ArrayList<>();
		for(String item : keywordsTokens){
			if(item.length() == 0)
			{
				indexes.add(keywordsTokens.indexOf(item));
			}
		}
		for(int i : indexes){
			keywordsTokens.remove(i);
		}
		return keywordsTokens;
	}


	@Override
	public PageResult findPage(TbItem item, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbItemExample example=new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		
		if(item!=null){			
						if(item.getTitle()!=null && item.getTitle().length()>0){
				criteria.andTitleLike("%"+item.getTitle()+"%");
			}
			if(item.getSellPoint()!=null && item.getSellPoint().length()>0){
				criteria.andSellPointLike("%"+item.getSellPoint()+"%");
			}
			if(item.getBarcode()!=null && item.getBarcode().length()>0){
				criteria.andBarcodeLike("%"+item.getBarcode()+"%");
			}
			if(item.getImage()!=null && item.getImage().length()>0){
				criteria.andImageLike("%"+item.getImage()+"%");
			}
			if(item.getStatus()!=null && item.getStatus().length()>0){
				criteria.andStatusLike("%"+item.getStatus()+"%");
			}
			if(item.getItemSn()!=null && item.getItemSn().length()>0){
				criteria.andItemSnLike("%"+item.getItemSn()+"%");
			}
			if(item.getIsDefault()!=null && item.getIsDefault().length()>0){
				criteria.andIsDefaultLike("%"+item.getIsDefault()+"%");
			}
			if(item.getSellerId()!=null && item.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+item.getSellerId()+"%");
			}
			if(item.getCartThumbnail()!=null && item.getCartThumbnail().length()>0){
				criteria.andCartThumbnailLike("%"+item.getCartThumbnail()+"%");
			}
			if(item.getCategory()!=null && item.getCategory().length()>0){
				criteria.andCategoryLike("%"+item.getCategory()+"%");
			}
			if(item.getBrand()!=null && item.getBrand().length()>0){
				criteria.andBrandLike("%"+item.getBrand()+"%");
			}
			//if(item.getSpec()!=null && item.getSpec().length()>0){
			//	criteria.andSpecLike("%"+item.getSpec()+"%");
			//}
			if(item.getSeller()!=null && item.getSeller().length()>0){
				criteria.andSellerLike("%"+item.getSeller()+"%");
			}
	
		}
		
		Page<TbItem> page= (Page<TbItem>)itemMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public List<TbItem> findTopSeller(int num) {
		if(num <= 0 ) {
			num = 5;
		}
		return itemMapper.selectTopSeller(num);
	}

	@Override
	public List<TbItem> findNew() {

		TbItemExample example  = new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andCategoryEqualTo("New-Arrival");
		List<TbItem>  listOfNew = itemMapper.selectByExample(example);
		return listOfNew;
	}


}
