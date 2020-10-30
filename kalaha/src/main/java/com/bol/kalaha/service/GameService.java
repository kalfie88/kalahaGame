package com.bol.kalaha.service;

import com.bol.kalaha.entities.Board;
import com.bol.kalaha.entities.PlayRequest;
import com.bol.kalaha.entities.PlayResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author kalfie
 */

@Service
@NoArgsConstructor
public class GameService {

    @Autowired
    private Board board;
    private int pitIndex;
    private int stones;

    /**
     * Handles the logic of the game, so depending on who is playing
     * we perform the correspondent move, if the game is over then we
     * return the message with the winner, otherwise we return a message
     * indicating which player is next to play.
     *
     * @param request play of the correspondent player
     * @return playResponse with the board status
     */
    public PlayResponse performPlay(PlayRequest request) {
        boolean isGameOver;
        String message;
        String player;
        initializePlay(request);

        if (board.isPlayerOne()) {
            isGameOver = makeMove(board.getOnePit(), board.getTwoPit(), true);
            player = "Player 1";

            if (isGameOver)
                message = "Game Over!! " + board.getWinner();
            else
                message = "Next turn -> Player 1";

        } else {
            isGameOver = makeMove(board.getTwoPit(), board.getOnePit(), false);
            player = "Player 2";

            if (isGameOver)
                message = "Game Over!! " + board.getWinner();
            else
                message = "Next turn -> Player 2";

        }

        return PlayResponse.builder()
                .board(board)
                .turn(player)
                .message(message)
                .build();
    }

    /**
     * We need to initialize the values of the play and the board
     *
     * @param request will bring the play the current player is doing
     */
    private void initializePlay(PlayRequest request) {
        Optional.ofNullable(request.getBoard()).ifPresent(b -> board = b);
        Optional.of(request.getPitIndex()).ifPresent(i -> pitIndex = i);
        Optional.of(request.getStones()).ifPresent(s -> stones = s);

        if (request.isPlayerOne()) {
            board.setPlayerOne(true);
            board.getOnePit().set(pitIndex, 0);

        } else {
            board.setPlayerOne(false);
            board.getTwoPit().set(pitIndex, 0);
        }
    }

    /**
     * Here we'll have several paths with the rules of the game, the first path
     * will be the normal iteration where the player making the play will move
     * the stones in it's own pit. Otherwise it will move to the pits of the opponent
     * until there's no more stones to move, or the last one falls in the Mancala.
     *
     * @param firstPit  pit from the player making the move
     * @param secondPit pit from the opponent player
     * @param isOne     this flag controls which player is playing
     * @return is the game is over or not
     */
    private Boolean makeMove(List<Integer> firstPit, List<Integer> secondPit, boolean isOne) {
        int j = 0;
        int i = pitIndex;

        while (stones > 0) {

            if (i < firstPit.size()) { //Normal iteration
                //If is the last stone and the pit was empty
                if (i == stones && firstPit.get(i) == 0) {
                    takeStones(i, firstPit, secondPit, isOne);
                }

                firstPit.set(i, firstPit.get(i) + 1);
                i++;
                stones--;

            } else if (i == firstPit.size() && i == stones - 1) {
                //If the last stone goes into the mancala
                updateMancala(isOne, 1);
                board.setPlayerOne(isOne);

            } else {
                secondPit.set(j, secondPit.get(j) + 1);
                j++;
                stones--;
            }

            //Extra check if the numStones > numPits it needs to go around again...
            if (stones > 0 && secondPit.get(j) == secondPit.size() - 1) {
                i = 0;
            }
        }

        updatePits(firstPit, secondPit, isOne);
        board.setPlayerOne(!isOne);
        return isGameOver();
    }

    /**
     * This contains the logic to take stones from the opponent, so if we move a stone
     * to an empty pit, we can take our stone and the stones in the opponent's
     * correspondent pit and move them to the Mancala of the current player.
     *
     * @param pitIndex  which pit are we taking stones from
     * @param firstPit  pit of the current player
     * @param secondPit pit of the opponent player
     * @param isOne     this flags handles the current player
     */
    private void takeStones(int pitIndex, List<Integer> firstPit, List<Integer> secondPit, boolean isOne) {
        int numStones = firstPit.get(pitIndex) + secondPit.get(pitIndex);
        firstPit.set(pitIndex, 0);
        secondPit.set(pitIndex, 0);

        updatePits(firstPit, secondPit, isOne);
        updateMancala(isOne, numStones);
    }

    /**
     * Check if one of the player has all of it's pits empty,
     * if it does it's game over.
     * @return flag if the game is over
     */
    private boolean isGameOver() {
        if (isPitOneEmpty() || isPitTwoEmpty()) {
            clearPits();
            board.setWinner(getWinner());
            return true;
        }

        return false;
    }

    /**
     * Here we check the contents of the mancalas and determine which player
     * is the winner
     *
     * @return winner
     */
    private String getWinner() {
        String player;

        if (board.getMancalaOne() == board.getMancalaTwo())
            return "We got a tie!!";

        if ((board.getMancalaOne() > board.getMancalaTwo()))
            player = "One";
        else
            player = "Two";

        return String.format("Congratulations Player %s you are the winner", player);
    }

    /**
     * If the game is over, we need to clear any pit that still has some
     * stones in it, and we need to move them to the correspondent Mancala
     */
    private void clearPits() {
        int numStones;
        if (isPitOneEmpty()) {
            numStones = board.getTwoPit().stream()
                    .reduce(0, Integer::sum);
            updateMancala(false, numStones);

        } else {
            numStones = board.getOnePit().stream()
                    .reduce(0, Integer::sum);
            updateMancala(true, numStones);
        }
    }

    /**
     * Checks if the pit from player 1 is empty
     *
     * @return if the pit is empty
     */
    private boolean isPitOneEmpty() {
        return board.getOnePit().stream().allMatch(i -> i == 0);
    }

    /**
     * Checks if the pit from player 2 is empty
     *
     * @return if the pit is empty
     */
    private boolean isPitTwoEmpty() {
        return board.getTwoPit().stream().allMatch(i -> i == 0);
    }

    /**
     * After the move, we need to update the fits with the new
     * number os stones in each, depending on the correspondent player
     *
     * @param firstPit  pit of the current player
     * @param secondPit pit of the opponent player
     * @param isOne     the current player
     */
    private void updatePits(List<Integer> firstPit, List<Integer> secondPit, boolean isOne) {
        if (isOne) {
            board.setOnePit(firstPit);
            board.setTwoPit(secondPit);

        } else {
            board.setOnePit(secondPit);
            board.setTwoPit(firstPit);
        }
    }

    /**
     * If the last stone falls in the Mancala we need to update
     * the value in the board for the correspondent player, or if we
     * took some stones from the opponent player we can use this
     * method to update the value of the Mancala
     *
     * @param isOne  the current player
     * @param stones the amount of stones to update
     */
    private void updateMancala(boolean isOne, int stones) {
        if (isOne)
            board.setMancalaOne(board.getMancalaOne() + stones);
        else
            board.setMancalaTwo(board.getMancalaTwo() + stones);
    }
}
