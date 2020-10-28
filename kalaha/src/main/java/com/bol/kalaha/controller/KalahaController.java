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

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class KalahaController {

    @Autowired
    private final KalahaService kalahaService;

    private Board board;

    /**
     * Endpoint to call the index page
     * @param model
     * @return String
     */
    @RequestMapping
    public String initGame(Model model) {
        model.addAttribute("request", new Play());

        return "index";
    }

    /**
     * Endpoint to connect with the service and retrieve the
     * photos according to the search criteria
     * @param request
     * @return String
     */
    @RequestMapping(path = "/play", method = RequestMethod.POST)
    public String getAllPhotos(Play request) {
        board = kalahaService.playGame(request);

        return "redirect:/photos"; //TODO
    }

  /*  @PostMapping(value = "/play")
    public ResponseEntity<?> play(@RequestBody Play request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();

        }

        return ResponseEntity.ok().body(kalahaService.playGame(request));

    }*/
}
