package com.paramount.shopping.service.impl;

import com.paramount.ServerApplication;
import com.paramount.shopping.domian.TbGoodsDesc;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Log4j
@RunWith(value = SpringRunner.class)
@SpringBootTest(classes=ServerApplication.class)
@Import(ServerApplication.class)
class GoodsDescServiceImplTest {

    @Mock
    GoodsDescServiceImpl goodsDescService;

    @Before
    public void before(){
        List<TbGoodsDesc> lists = Arrays.asList();
        when(goodsDescService.findAll()).thenReturn(lists);
    }

    @Test
    void findAll() {
    }

    @Test
    void findPage() {
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void findOne() {
    }

    @Test
    void delete() {
    }

    @Test
    void findPage1() {
    }
}