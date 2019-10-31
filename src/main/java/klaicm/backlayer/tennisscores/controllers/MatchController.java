package klaicm.backlayer.tennisscores.controllers;

import klaicm.backlayer.tennisscores.model.Match;
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
    public Set<Match> listMatches() {
        Set<Match> allMatches = matchJpaService.findAll();

        return allMatches;
    }

    @PostMapping(path = "/saveMatch", consumes = "application/json", produces = "application/json")
    public void saveMatch(@RequestBody Match match) {
        matchJpaService.save(match);
    }

    @RequestMapping(value =  "/importFile/{fileName}")
    public void saveMatchesByExcel(@PathVariable("fileName") String fileName) {
        matchJpaService.importXlsxFile(fileName);
    }

}
