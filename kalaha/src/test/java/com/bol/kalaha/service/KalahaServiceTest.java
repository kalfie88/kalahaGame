package com.bol.kalaha.service;

import com.bol.kalaha.entities.Board;
import com.bol.kalaha.entities.Play;
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
        int pitIndex = 1;
        boolean isPlayerOne = true;
        List<Integer> onePit = Arrays.asList(6, 0, 6, 6, 6, 6);
        List<Integer> twoPit = Arrays.asList(6, 0, 6, 6, 6, 6);
        Board board = new Board();
        board.setMancalaOne(0);
        board.setMancalaTwo(0);
        board.setOnePit(onePit);
        board.setTwoPit(twoPit);
        board.setWinner(null);

        Play play = Play.builder()
                .stones(stones)
                .pitIndex(pitIndex)
                .isPlayerOne(isPlayerOne)
                .board(board)
                .message("Next turn -> Player 2")
                .build();

        when(gameService.performPlay(play)).thenReturn(play);
        Play response = kalahaService.playGame(play);
        assertThat(response).isEqualTo(play);
    }

}
