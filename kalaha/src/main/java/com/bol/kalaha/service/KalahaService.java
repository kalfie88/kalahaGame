package com.bol.kalaha.service;

import com.bol.kalaha.entities.PlayRequest;
import com.bol.kalaha.entities.PlayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kalfie
 */

@Service
public class KalahaService {

    @Autowired
    private GameService game;

    /**
     * Starts the game
     *
     * @param request play the current player did
     * @return the updated board after the play
     */
    public PlayResponse playGame(PlayRequest request) {

        return game.performPlay(request);
    }

}
