package com.bol.kalaha.service;

import com.bol.kalaha.entities.Board;
import com.bol.kalaha.entities.Play;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Game {

    @Autowired
    private final Board board;
    private int pitIndex;
    private int stones;

    public Board play(Play request) {
        initializePlay(request);
        performPlay();
        clearPits();
        board.setWinner(checkWinner());

        return board;
    }

    private String checkWinner() {
       String player;

        if (board.getMancalaOne() == board.getMancalaTwo())
            return "We got a tie!!";

        if ((board.getMancalaOne() > board.getMancalaTwo()))
            player = "One";
        else
            player = "Two";

        return  String.format("Congratulations Player %s you are the winner", player);
    }

    private void initializePlay(Play request) {
        this.pitIndex = request.getPitIndex();
        this.stones = request.getStones();

        if (request.isPlayerOne()) {
            board.setPlayerOne(true);
            board.getOnePit().set(pitIndex, 0);
        } else
            board.getTwoPit().set(pitIndex, 0);
    }

    private void performPlay() {
        while (!isGameOver()) {
            if (board.isPlayerOne())
                makeMove(board.getOnePit(), board.getTwoPit(), true);

            else
                makeMove(board.getTwoPit(), board.getOnePit(), false);
        }
    }

    private void makeMove(List<Integer>  firstPit, List<Integer>  secondPit, boolean isOne) {
        // Can make some math before to see how many stones and pits available do I have
        // and how many stones I'm carrying over to the other player's pit
        // maybe change this for a while... and just split the amount of stones I have

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

            } else if (i == firstPit.size() && i == stones-1) {
                //If last stone goes into the mancala
                addToMancala(isOne, 1);
                board.setPlayerOne(isOne);

            }   else {
                secondPit.set(j, secondPit.get(j) + 1);
                j++;
                stones--;
            }

            //Extra check if the numStones > numPits it needs to go around again...
            if (stones > 0 && secondPit.get(j) == secondPit.size() -1) {
                i = 0;
            }
        }

        updatePits(firstPit, secondPit, isOne);
    }

    private void takeStones(int pitIndex, List<Integer> firstPit, List<Integer>  secondPit, boolean isOne) {
        int numStones = firstPit.get(pitIndex) + secondPit.get(pitIndex);
        firstPit.set(pitIndex, 0);
        secondPit.set(pitIndex, 0);

        updatePits(firstPit, secondPit, isOne);
        addToMancala(isOne, numStones);
    }

    private boolean isGameOver() {
        return isPitOneEmpty() || isPitTwoEmpty();
    }

    private void clearPits() {
        int numStones;
        if (isPitOneEmpty()) {
            numStones = board.getTwoPit().stream()
                    .reduce(0, Integer::sum);
            addToMancala(false, numStones);

        } else {
            numStones = board.getOnePit().stream()
                    .reduce(0, Integer::sum);
            addToMancala(true, numStones);
        }
    }

    public boolean isPitOneEmpty() {
        return board.getOnePit().stream().allMatch(i -> i == 0);
    }

    public boolean isPitTwoEmpty() {
        return board.getTwoPit().stream().allMatch(i -> i == 0);
    }

    public void updatePits(List<Integer> firstPit, List<Integer> secondPit, boolean isOne) {
        if (isOne) {
            board.setOnePit(firstPit);
            board.setTwoPit(secondPit);

        } else {
            board.setOnePit(secondPit);
            board.setTwoPit(firstPit);
        }
    }

    public void addToMancala(boolean isOne, int stones) {
        if (isOne)
            board.setMancalaOne(board.getMancalaOne() + stones);
        else
            board.setMancalaTwo(board.getMancalaTwo() + stones);
    }
}
