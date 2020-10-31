package com.bol.kalaha.controller;

import com.bol.kalaha.entities.Board;
import com.bol.kalaha.entities.Play;
import com.bol.kalaha.service.KalahaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
     * @return String
     */
    @RequestMapping
    public String initGame(Model model) {
        model.addAttribute("request", new Play());

        return "index";
    }


    @RequestMapping(path = "/play", method = RequestMethod.POST)
    public String play(Play request) {
        response = kalahaService.playGame(request);

        return "redirect:/display";
    }

    @RequestMapping(path = {"/display"})
    public String displayPlay(Model model) {
        Board board = response.getBoard();
        model.addAttribute("board", board);

        return "redirect:/";
    }

    /**
     * Starts the game
     *
     * @param request play of the current player
     * @return the board updated
     */
   /* @RequestMapping(path = "/play", method = RequestMethod.POST)
    public ResponseEntity<Play> play(Play request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();

        }

        return ResponseEntity.ok().body(kalahaService.playGame(request));
    }
*/
}
