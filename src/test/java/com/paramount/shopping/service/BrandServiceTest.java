package com.paramount.shopping.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paramount.ServerApplication;
import com.paramount.shopping.dao.TbBrandMapper;
import com.paramount.shopping.domian.TbBrand;
import com.paramount.shopping.domian.response.PageResult;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServerApplication.class)
@Import(ServerApplication.class)
public class BrandServiceTest {

    @Autowired
    TbBrandMapper brandMapper;

    @Test
    public void findAll() {
        List<TbBrand> lists = brandMapper.selectByExample(null);
        assertEquals(true, !lists.isEmpty());
    }

    @Test
    public void findByPage() {
        PageHelper.startPage(1, 5);
        Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);
        List<TbBrand> row = page.getResult();
        assertEquals(true, row.size() == 5 );
    }

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void selectOptionList() {
    }
}