package test;

import com.alibaba.fastjson.JSON;
import com.paramount.ServerApplication;
import com.paramount.shopping.dao.TbItemMapper;
import com.paramount.shopping.domian.TbItem;
import com.paramount.shopping.repository.TbItemRepositories;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.PartialUpdate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServerApplication.class)
@Import(ServerApplication.class)
public class SolrJTest {

    @Autowired
    SolrTemplate solrTemplate;


    @Autowired
    TbItemMapper tbItemMapper;

    @Value("${spring.data.solr.core}")
    private String coreName;
    @Test
    public  void test(){
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(1369289l);
        solrTemplate.saveBean(coreName,tbItem);
        solrTemplate.commit(coreName);
    }


    @Autowired
    private TbItemRepositories tbItemRepositories;

    @Test
    public void testGet(){
        /*TbItem tbItem = tbItemRepositories.findById(Long.valueOf(1369289)).get();
        System.out.println(tbItem);*/
        TbItem tbItem = solrTemplate.getById(coreName, (Object) Long.valueOf(1369289), TbItem.class).get();
        System.out.println(tbItem);
    }

    @Test
    public void testUpdate(){
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(1369289l);
        tbItem.setTitle("updateTest");
        solrTemplate.saveBean(coreName,tbItem);
        solrTemplate.commit(coreName);
    }

    @Test
    public void testSave(){
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(1369289l);
        tbItemRepositories.save(tbItem);
    }

    @Test
    public void testDeleteAll(){
        SolrDataQuery query = new SimpleQuery("*:*");
        solrTemplate.delete(coreName,query);
        solrTemplate.commit(coreName);
    }

    @Test
    public void testDeleteById(){
        solrTemplate.deleteByIds(coreName,String.valueOf(1369289l));

        solrTemplate.commit(coreName);
    }


    @Test
    public void BatchInsertion(){
        List<TbItem> beans = tbItemMapper.selectByExample(null);

        for(TbItem item: beans){
            Map specMap = JSON.parseObject(item.getSpec(), Map.class);
            item.setSpecMap(specMap);
        }
        System.out.println(beans.size());
         solrTemplate.saveBeans(coreName,beans);
         solrTemplate.commit(coreName);
    }

    @Test
    public void testQueryPage(){
        SimpleQuery simpleQuery = new SimpleQuery("*:*");
        simpleQuery.setOffset(20l);
        simpleQuery.setRows(20);

        ScoredPage<TbItem> page = solrTemplate.queryForPage(coreName, simpleQuery, TbItem.class);

        for (TbItem item : page.getContent()){
            System.out.println("-------" + page.getContent().indexOf(item) +"--------");
            System.out.println(item.getId());
            System.out.println(item.getTitle());
            System.out.println(item.getPrice());
            System.out.println(item.getImage());
            System.out.println(item.getCategory());
            System.out.println(item.getBrand());
            System.out.println("-----------------");

        }

        System.out.println("Total elements : " + page.getTotalElements());
        System.out.println("Total pages : " + page.getTotalPages());

    }

    @Test
    public void testQueryPageCondition(){
        //SELECT * from tb_item where category like "%New%" and brand like "%Max%";
        SimpleQuery simpleQuery = new SimpleQuery("*:*");

        Criteria criterial = new Criteria("item_category").contains("New");
        criterial = criterial.and("item_brand").contains("Max");

        simpleQuery.addCriteria(criterial);

        simpleQuery.setOffset(20l);  //从第几条记录
        simpleQuery.setRows(20); //一页几条记录'

        ScoredPage<TbItem> page = solrTemplate.queryForPage(coreName, simpleQuery, TbItem.class);

        for (TbItem item : page.getContent()){
            System.out.println("-------" + page.getContent().indexOf(item) +"--------");
            System.out.println(item.getId());
            System.out.println(item.getTitle());
            System.out.println(item.getPrice());
            System.out.println(item.getImage());
            System.out.println(item.getCategory());
            System.out.println(item.getBrand());
            System.out.println("-----------------");

        }

        System.out.println("Total elements : " + page.getTotalElements());
        System.out.println("Total pages : " + page.getTotalPages());

    }

}
