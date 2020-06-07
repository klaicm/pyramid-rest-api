package klaicm.backlayer.tennisscores.controllers;

import klaicm.backlayer.tennisscores.model.Match;
import klaicm.backlayer.tennisscores.model.Round;
import klaicm.backlayer.tennisscores.services.jpadata.MatchJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class MatchController {

    @Autowired
    MatchJpaService matchJpaService;

    @GetMapping("/allMatches")
    public Set<Match> getAllMatches() {
        return matchJpaService.findAll();
    }

    @GetMapping("/match/{id}")
    public Match getMatch(@PathVariable("id") Long id, Model model) {
        return matchJpaService.findById(id);
    }

    @PostMapping(path = "/saveMatch", consumes = "application/json", produces = "application/json")
    public void savePlayer(@RequestBody Match match) {
        matchJpaService.save(match);
    }

    @GetMapping("playerMatches/{id}")
    public Set<Match> getPlayerMatches(@PathVariable("id") Long id, Model model) { return matchJpaService.findMatchesByPlayerId(id); }

    @GetMapping("roundMatches/{id}")
    public Set<Match> findMatchesByRoundId(@PathVariable("id") Long id, Model model) { return matchJpaService.findMatchesByRoundId(id); }

}
