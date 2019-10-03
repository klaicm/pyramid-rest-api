package klaicm.backlayer.tennisscores.controllers;

import klaicm.backlayer.tennisscores.model.Match;
import klaicm.backlayer.tennisscores.services.jpadata.MatchJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MatchController {

    @Autowired
    MatchJpaService matchJpaService;

    @GetMapping("/allMatches")
    public Set<Match> listMatches() {
        Set<Match> allMatches = matchJpaService.findAll();

        return allMatches;
    }

}
