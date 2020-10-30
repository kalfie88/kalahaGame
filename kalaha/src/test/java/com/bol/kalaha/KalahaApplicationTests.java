package com.bol.kalaha;

import com.bol.kalaha.entities.Board;
import com.bol.kalaha.entities.PlayRequest;
import com.bol.kalaha.entities.PlayResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.gson.Gson;
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
import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    private Gson gson;


    @Before
    public void setup() throws Exception {
        WireMock.reset();

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        this.gson = new Gson();
        Resource fixture = new ClassPathResource("playResponse.json");
        this.expectedJson =
                new String(Files.readAllBytes(Paths.get(fixture.getFile().getAbsolutePath())));

        stubFor(get(urlMatching("/play"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(this.expectedJson)));

    }

    @Test
    public void play_valid() throws Exception {
        int stones = 6;
        int pitIndex = 2;
        boolean isPlayerOne = true;
        List<Integer> onePit = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> twoPit = Arrays.asList(0, 0, 0, 0, 0, 0);

        Board board = new Board();
        board.setMancalaOne(10);
        board.setMancalaTwo(2);
        board.setOnePit(onePit);
        board.setTwoPit(twoPit);
        board.setWinner("one");

        PlayRequest request = PlayRequest.builder()
                .stones(stones)
                .pitIndex(pitIndex)
                .isPlayerOne(isPlayerOne)
                .board(board)
                .build();

        PlayResponse expectedResponse = gson.fromJson(this.expectedJson, PlayResponse.class);

        mvc.perform(MockMvcRequestBuilders.post("/play")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

    }

}
