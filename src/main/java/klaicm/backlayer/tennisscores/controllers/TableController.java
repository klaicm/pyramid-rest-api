package klaicm.backlayer.tennisscores.controllers;

import klaicm.backlayer.tennisscores.model.Player;
import klaicm.backlayer.tennisscores.services.jpadata.PlayerJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class TableController {

    @Autowired
    PlayerJpaService playerJpaService;

    @GetMapping("/table")
    public Set<Player> getTable(Model model) {
        Set<Player> allPlayers = playerJpaService.findAll();
        model.addAttribute("players", allPlayers);
        return allPlayers;
        // return "table/index";
    }

}
