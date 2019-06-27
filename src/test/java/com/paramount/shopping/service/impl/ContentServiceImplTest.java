package com.paramount.shopping.service.impl;

import com.paramount.ServerApplication;
import com.paramount.shopping.domian.TbContent;
import com.paramount.shopping.domian.response.PageResult;
import com.paramount.shopping.service.ContentService;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@Log4j
@RunWith(value = SpringRunner.class)
@SpringBootTest(classes=ServerApplication.class)
@Import(ServerApplication.class)
class ContentServiceImplTest {

    @Mock
    ContentService contentServiceImpl;
    TbContent c1;
    TbContent c2;
    TbContent c3;


    @Before
    public void before(){
        c1 = new TbContent();
        c1.setId(1l);
        c1.setTitle("New Arrivals");
        c2 = new TbContent();
        c2.setId(2l);
        c2.setTitle("Baby Stuff");
        c3 = new TbContent();
        c3.setId(3l);
        c3.setTitle("Party Supplies");
        List<TbContent> lists = asList(c1, c2, c3);

        when(contentServiceImpl.findAll()).thenReturn(lists);

        PageResult pageResult = new PageResult(7, lists);
        when(contentServiceImpl.findPage(2,4)).thenReturn(pageResult);
        when(contentServiceImpl.findAll()).thenReturn(lists);
        when(contentServiceImpl.findAll()).thenReturn(lists);
        when(contentServiceImpl.findAll()).thenReturn(lists);
        when(contentServiceImpl.findOne(3l)).thenReturn(c3);
        when(contentServiceImpl.findOne(2l)).thenReturn(c2);
        when(contentServiceImpl.findOne(1l)).thenReturn(c1);
    }



    @Test
    void findAll() {
        assertEquals(3, contentServiceImpl.findAll().size());
    }

    @Test
    void findPage() {

        assertEquals(PageResult.class,contentServiceImpl.findPage(2,4).getClass());
        assertEquals(3, contentServiceImpl.findPage(2,4).getRows().size());
        assertEquals(7 ,contentServiceImpl.findPage(2,4).getTotal());
    }

    @Test
    void add() {
        doNothing().when(contentServiceImpl).add(isA(TbContent.class));
        contentServiceImpl.add(c1);
        verify(contentServiceImpl, times(1)).add(c1);
    }

    @Test
    void update() {
        doNothing().when(contentServiceImpl).update(isA(TbContent.class));
        contentServiceImpl.update(c2);
        verify(contentServiceImpl, times(0)).add(c1);
        verify(contentServiceImpl, times(1)).add(c2);
    }

    @Test
    void findOne() {
        assertEquals( "New Arrivals", contentServiceImpl.findOne(1l).getTitle());
        assertEquals( "Baby Stuff", contentServiceImpl.findOne(2l).getTitle());
        assertEquals( "Party Supplies", contentServiceImpl.findOne(3l).getTitle());
    }

    @Test
    void delete() {
        doNothing().when(contentServiceImpl).delete(isA(Long[].class));
        Long[] idsList = new Long[]{1L,2L,3L};
        contentServiceImpl.delete(idsList);
        verify(contentServiceImpl, times(1)).delete(idsList);
    }


}