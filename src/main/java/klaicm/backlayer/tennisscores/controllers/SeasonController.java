package klaicm.backlayer.tennisscores.controllers;

import klaicm.backlayer.tennisscores.model.Season;
import klaicm.backlayer.tennisscores.services.jpadata.SeasonJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class SeasonController {

    @Autowired
    SeasonJpaService seasonJpaService;

    @GetMapping("/allSeasons")
    public Set<Season> getAllRounds() {
        return seasonJpaService.findAll();
    }

    @PostMapping(path ="/addSeason", consumes = "application/json", produces = "application/json")
    public void addRound(@RequestBody Season season) {
        seasonJpaService.save(season);
    }
}
