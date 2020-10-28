package com.bol.kalaha.service;

import com.bol.kalaha.entities.Board;
import com.bol.kalaha.entities.Play;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KalahaService {

    @Autowired
    private Game game;

   public Board playGame(Play request) {
       return game.play(request);
   }



}
