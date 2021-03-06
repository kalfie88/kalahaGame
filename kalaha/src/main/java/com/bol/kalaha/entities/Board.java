package com.bol.kalaha.entities;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author kalfie
 */

@Component
@Data
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

}
