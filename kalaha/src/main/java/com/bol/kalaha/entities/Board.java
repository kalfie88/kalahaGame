package com.bol.kalaha.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Data
@Builder
public class Board {

    private static final int NUM_STONES = 6;
    private static final int NUM_PITS = 6;

    private List<Integer> onePit;
    private List<Integer> twoPit;
    private int mancalaOne;
    private int mancalaTwo;
    private boolean isPlayerOne;
    private String winner;

    public Board() {
        this.onePit = IntStream.generate(() -> NUM_STONES)
                .limit(NUM_PITS)
                .boxed()
                .collect(Collectors.toList());

        this.twoPit = this.onePit;

    }


    public boolean isPitOneEmpty() {
        return Stream.of(this.onePit)
                .allMatch(Collections.singletonList(this.onePit).get(0)::equals);
    }

    public boolean isPitTwoEmpty() {
        return Stream.of(this.twoPit)
                .allMatch(Collections.singletonList(this.twoPit).get(0)::equals);
    }

    public void updatePits(List<Integer> firstPit, List<Integer> secondPit, boolean isOne) {
        if (isOne) {
            this.onePit = firstPit;
            this.twoPit = secondPit;

        } else {
            this.onePit = secondPit;
            this.twoPit = firstPit;
        }
    }

    public void addToMancala(boolean isOne, int stones) {
        if (isOne)
            this.mancalaOne += stones;
        else
            this.mancalaTwo += stones;
    }

}
