package com.paramount.shopping.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest
class GoodsControllerTest {

    @Autowired
    private MockMvc mockMvc;   // <2>

    @org.junit.Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))    // <3>

                .andExpect(status().isOk())  // <4>

                .andExpect(view().name("home"))  // <5>

                .andExpect(content().string(           // <6>
                        containsString("Welcome to...")));
    }





}