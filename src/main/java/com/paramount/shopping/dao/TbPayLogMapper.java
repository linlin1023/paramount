package com.paramount.shopping.dao;

import java.util.List;

import com.paramount.shopping.domian.TbPayLog;
import com.paramount.shopping.domian.TbPayLogExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TbPayLogMapper {
    int countByExample(TbPayLogExample example);

    int deleteByExample(TbPayLogExample example);

    int deleteByPrimaryKey(String outTradeNo);

    int insert(TbPayLog record);

    int insertSelective(TbPayLog record);

    List<TbPayLog> selectByExample(TbPayLogExample example);

    TbPayLog selectByPrimaryKey(String outTradeNo);

    int updateByExampleSelective(@Param("record") TbPayLog record, @Param("example") TbPayLogExample example);

    int updateByExample(@Param("record") TbPayLog record, @Param("example") TbPayLogExample example);

    int updateByPrimaryKeySelective(TbPayLog record);

    int updateByPrimaryKey(TbPayLog record);
}