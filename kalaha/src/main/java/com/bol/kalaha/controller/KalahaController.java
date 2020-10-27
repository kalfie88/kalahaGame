package com.bol.kalaha.controller;

import com.bol.kalaha.entities.Play;
import com.bol.kalaha.service.KalahaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kalaha/v1")
@RequiredArgsConstructor
public class KalahaController {

    @Autowired
    private final KalahaService kalahaService;

    @PostMapping(value = "/play")
    public ResponseEntity<?> play(@RequestBody Play request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();

        }

        return ResponseEntity.ok().body(kalahaService.playGame(request));

    }
}
