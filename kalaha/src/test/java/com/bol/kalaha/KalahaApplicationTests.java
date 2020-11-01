package com.bol.kalaha;

import com.bol.kalaha.entities.Play;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.http.HttpHeaders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author kalfie
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 8089)
public class KalahaApplicationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;
    private String expectedJson;

    @Before
    public void setup() throws Exception {
        WireMock.reset();

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        Resource fixture = new ClassPathResource("playResponse.json");
        this.expectedJson =
                new String(Files.readAllBytes(Paths.get(fixture.getFile().getAbsolutePath())));

        stubFor(get(urlMatching("/kalaha/v1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(this.expectedJson)));

    }

    @Test
    public void playEndpointOK() throws Exception {

        Play request = Play.builder()
                .stones(6)
                .pitIndex(1)
                .isPlayerOne(true)
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/kalaha/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

}
