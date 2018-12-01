package com.paramount.admin.service;

import com.paramount.admin.vo.BeanField;
import com.paramount.admin.vo.GenerateInput;

import java.util.List;

/**
 * Created by szz on 2018/4/5 12:31.
 * Email szhz186@gmail.com
 */
public interface GenerateService {
    /**
     * 获取数据库表的信息
     * @param tableName
     * @return
     */
    List<BeanField> listBeanField(String tableName);

    /**
     * @param tableName
     * @return
     */
    String upperFirstChar(String tableName);

    void saveCode(GenerateInput input);
}
