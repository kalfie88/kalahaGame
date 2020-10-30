package com.bol.kalaha.controller;

import com.bol.kalaha.entities.PlayRequest;
import com.bol.kalaha.entities.PlayResponse;
import com.bol.kalaha.service.KalahaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kalfie
 */

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class KalahaController {

    @Autowired
    private final KalahaService kalahaService;

    /**
     * Endpoint to call the index page
     *
     * @param model
     * @return String
     */
    @RequestMapping
    public String initGame(Model model) {
        model.addAttribute("request", new PlayRequest());

        return "index";
    }

    /**
     * Starts the game
     *
     * @param request play of the current player
     * @return the board updated
     */
    @PostMapping(value = "/play")
    public ResponseEntity<PlayResponse> play(@RequestBody PlayRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();

        }

        return ResponseEntity.ok().body(kalahaService.playGame(request));
    }

}
