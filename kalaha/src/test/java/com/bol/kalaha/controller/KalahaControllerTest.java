package com.bol.kalaha.controller;

import com.bol.kalaha.entities.Board;
import com.bol.kalaha.entities.Play;
import com.bol.kalaha.service.KalahaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author kalfie
 */

@RunWith(MockitoJUnitRunner.class)
public class KalahaControllerTest {

    @Mock
    private KalahaService kalahaService;

    @InjectMocks
    private KalahaController kalahaController;

    @Test
    public void play_OK() {
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

        Play request = Play.builder()
                .stones(stones)
                .pitIndex(pitIndex)
                .isPlayerOne(isPlayerOne)
                .board(board)
                .build();

        Play expectedResponse = Play.builder()
                .board(board)
                .message("Game Over!!")
                .build();

        when(kalahaService.playGame(request)).thenReturn(expectedResponse);

        ResponseEntity<Play> response = kalahaController.playEndpoint(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    public void play_badRequest() {
        ResponseEntity<Play> response = kalahaController.playEndpoint(null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

}
