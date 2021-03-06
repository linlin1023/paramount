package com.paramount.admin.service;

import com.paramount.admin.domain.Dict;

import java.util.List;
import java.util.Map;

/**
 * Created by szz on 2018/4/2 20:11.
 * Email szhz186@gmail.com
 */
public interface DictService {
    Dict getByTypeAndK(String type, String k);

    void save(Dict dict);

    Dict getById(Long id);

    void update(Dict dict);

    void delete(Long id);

    List<Dict> listByType(String type);

    int count(Map<String, Object> params);

    List<Dict> list(Map<String, Object> params, Integer offset, Integer limit);
}
