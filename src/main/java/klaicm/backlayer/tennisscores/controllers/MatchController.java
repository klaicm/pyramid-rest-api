package klaicm.backlayer.tennisscores.controllers;

import klaicm.backlayer.tennisscores.model.Match;
import klaicm.backlayer.tennisscores.services.jpadata.MatchJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
public class MatchController {

    @Autowired
    MatchJpaService matchJpaService;

    @GetMapping("/allMatches")
    public Set<Match> listMatches() {

        return matchJpaService.findAll();
    }

    @PostMapping(path = "/saveMatch", consumes = "application/json", produces = "application/json")
    public void saveMatch(@RequestBody Match match) {
        matchJpaService.save(match);
    }

    @PostMapping(value =  "/importFile")
    public ResponseEntity<String> saveMatchesByExcel(@RequestParam("file") MultipartFile excelDataFile) {
        matchJpaService.importXlsxFile(excelDataFile);

        return new ResponseEntity<String>("Dokument " + excelDataFile.getOriginalFilename() + " učitan", HttpStatus.OK);
    }

}
