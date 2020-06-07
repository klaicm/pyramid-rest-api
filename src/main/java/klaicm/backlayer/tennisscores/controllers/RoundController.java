package klaicm.backlayer.tennisscores.controllers;

import klaicm.backlayer.tennisscores.model.Round;
import klaicm.backlayer.tennisscores.services.jpadata.RoundJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class RoundController {

    @Autowired
    RoundJpaService roundJpaService;

    @GetMapping("/allRounds")
    public Set<Round> getAllRounds() {
        return roundJpaService.findAll();
    }

    @PostMapping(path ="/addRound", consumes = "application/json", produces = "application/json")
    public void addRound(@RequestBody Round round) {
        roundJpaService.save(round);
    }
}
