package com.bol.kalaha.service;

import com.bol.kalaha.entities.Board;
import com.bol.kalaha.entities.PlayRequest;
import com.bol.kalaha.entities.PlayResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author kalfie
 */

@RunWith(MockitoJUnitRunner.class)
class KalahaServiceTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private KalahaService kalahaService;

    @Test
    void playGame_valid() {
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

        PlayResponse expectedResponse = PlayResponse.builder()
                .board(board)
                .turn("")
                .message("Game Over!!")
                .build();

        when(gameService.performPlay(request)).thenReturn(expectedResponse);
        PlayResponse response = kalahaService.playGame(request);
        assertThat(response).isEqualTo(expectedResponse);
    }

}
