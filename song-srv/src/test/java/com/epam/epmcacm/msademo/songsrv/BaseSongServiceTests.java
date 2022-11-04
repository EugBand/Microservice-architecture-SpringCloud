package com.epam.epmcacm.msademo.songsrv;

import com.epam.epmcacm.msademo.songsrv.controller.SongController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

public abstract class BaseSongServiceTests {

    @Autowired protected MockMvc mockMvc;
    @Autowired SongController songController;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(songController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
        mockMvc = standaloneMockMvcBuilder.build();
    }

}

