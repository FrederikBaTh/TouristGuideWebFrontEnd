package com.example.touristguide_del2;

import com.example.touristguide_del2.controller.TouristController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TouristGuideDel2ApplicationTests {

    @Autowired
    private TouristController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
