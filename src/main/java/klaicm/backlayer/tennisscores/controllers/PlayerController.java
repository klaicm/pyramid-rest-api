package klaicm.backlayer.tennisscores.controllers;

import klaicm.backlayer.tennisscores.model.ArchData;
import klaicm.backlayer.tennisscores.model.Match;
import klaicm.backlayer.tennisscores.model.Player;
import klaicm.backlayer.tennisscores.services.jpadata.ArchDataJpaService;
import klaicm.backlayer.tennisscores.services.jpadata.MatchJpaService;
import klaicm.backlayer.tennisscores.services.jpadata.PlayerJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PlayerController {

    @Autowired
    PlayerJpaService playerJpaService;

    @Autowired
    MatchJpaService matchJpaService;

    @Autowired
    ArchDataJpaService archDataJpaService;

    @GetMapping("/allPlayers")
    public Set<Player> getAllPlayers() {
        return playerJpaService.findAll();
    }

    @GetMapping("/player/{id}")
    public Player getPlayer(@PathVariable("id") Long id) {
        return playerJpaService.findById(id);
    }

    @GetMapping("/player/matches/{id}")
    public Set<Match> getMatchesOfPlayer(@PathVariable("id") Long id) {
        return matchJpaService.getPlayerMatches(id);
    }

    @GetMapping("/player/arch/{id}")
    public Set<ArchData> getPlayerArchData(@PathVariable("id") Long id) {
        return archDataJpaService.getArchDataByPlayerId(id);
    }

    @GetMapping("/elo-stats")
    public Map<String, Double> getWinProbability(@RequestParam String playerAElo, @RequestParam String playerBElo) {
        return playerJpaService.calculateProbabilityOfWin(Integer.parseInt(playerAElo), Integer.parseInt(playerBElo));
    }

}
