package com.bol.kalaha.service;

import com.bol.kalaha.entities.Board;
import com.bol.kalaha.entities.Play;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kalfie
 */

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Test
    public void performPlayValid() {
        List<Integer> onePit = Arrays.asList(6, 0, 7, 7, 7, 7);
        List<Integer> twoPit = Arrays.asList(7, 7, 6, 6, 6, 6);

        Board board = new Board();
        board.setMancalaOne(0);
        board.setMancalaTwo(0);
        board.setOnePit(onePit);
        board.setTwoPit(twoPit);
        board.setWinner(null);

        Play play = Play.builder()
                .stones(0)
                .pitIndex(0)
                .isPlayerOne(false)
                .board(board)
                .message("Next turn -> Player 1")
                .build();

        Play response = gameService.performPlay(play);
        assertThat(response).isEqualTo(play);
    }

    @Test
    public void performPlayGameOver() {
        List<Integer> onePit = Arrays.asList(1, 0, 0, 3, 4, 2);
        List<Integer> twoPit = Arrays.asList(0, 0, 0, 0, 0, 0);

        Board board = new Board();
        board.setMancalaOne(39);
        board.setMancalaTwo(25);
        board.setOnePit(onePit);
        board.setTwoPit(twoPit);
        board.setWinner("Congratulations Player One you are the winner");

        Play play = Play.builder()
                .stones(0)
                .pitIndex(0)
                .isPlayerOne(false)
                .board(board)
                .message("Game Over!! Congratulations Player One you are the winner")
                .build();

        Play response = gameService.performPlay(play);
        assertThat(response).isEqualTo(play);
    }
}
