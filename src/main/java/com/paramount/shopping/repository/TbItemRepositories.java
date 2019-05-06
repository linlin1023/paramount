package com.paramount.shopping.repository;

import com.paramount.shopping.domian.TbItem;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TbItemRepositories extends SolrCrudRepository<TbItem, Long> {
}
