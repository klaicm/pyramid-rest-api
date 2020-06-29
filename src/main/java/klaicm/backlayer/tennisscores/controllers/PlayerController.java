package klaicm.backlayer.tennisscores.controllers;

import klaicm.backlayer.tennisscores.model.Player;
import klaicm.backlayer.tennisscores.model.PlayerStats;
import klaicm.backlayer.tennisscores.services.jpadata.PlayerJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PlayerController {

    @Autowired
    PlayerJpaService playerJpaService;

    @GetMapping("/allPlayers")
    public Set<Player> getAllPlayers() {
        return playerJpaService.findAll();
    }

    @GetMapping("/player/{id}")
    public Player getPlayer(@PathVariable("id") Long id, Model model) {
        return playerJpaService.findById(id);
    }

    @PostMapping(path = "/addNewPlayer", consumes = "application/json", produces = "application/json")
    public void addNewPlayer(@RequestBody Player player) {

        PlayerStats playerStats = new PlayerStats();
        playerStats.setPlayer(player);
        playerStats.setCurrentRow(player.getPlayerStats().getCurrentRow());
        playerStats.setBestRow(player.getPlayerStats().getCurrentRow());
        playerStats.setBestStreak(0);
        playerStats.setCurrentStreak(0);

        player.setPlayerStats(playerStats);

        playerJpaService.save(player);
    }

    @PostMapping(path = "/savePlayer", consumes = "application/json", produces = "application/json")
    public void savePlayer(@RequestBody Player player) {

        PlayerStats playerStats = player.getPlayerStats();

        playerStats.setPlayer(player);

        playerJpaService.save(player);
    }

    @PostMapping(path = "/deletePlayer{id}")
    public void deletePlayer(@PathVariable("id") Long id, Model model) {
        playerJpaService.deleteById(id);
    }

}
