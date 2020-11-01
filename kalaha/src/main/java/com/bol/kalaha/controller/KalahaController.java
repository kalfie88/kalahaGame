package com.bol.kalaha.controller;

import com.bol.kalaha.entities.Play;
import com.bol.kalaha.service.KalahaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author kalfie
 */

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class KalahaController {

    @Autowired
    private final KalahaService kalahaService;
    private Play response;

    /**
     * Endpoint to call the index page
     *
     * @param model to bind the values to the UI
     * @return returns the index page
     */
    @RequestMapping
    public String initGame(Model model) {
        model.addAttribute("request", new Play());

        return "index";
    }

    /**
     * Submits a play
     *
     * @param request the values of the play
     * @return redirects to index
     */
    @RequestMapping(path = "/play", method = RequestMethod.POST)
    public String play(Play request) {
        response = kalahaService.playGame(request);

        return "redirect:/";
    }

    /**
     * Endpoint to connect to the game
     *
     * @param request play of the current player
     * @return the board updated
     */
    @PostMapping(path = "/kalaha/v1")
    public ResponseEntity<Play> playEndpoint(Play request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();

        }

        return ResponseEntity.ok().body(kalahaService.playGame(request));
    }

}
