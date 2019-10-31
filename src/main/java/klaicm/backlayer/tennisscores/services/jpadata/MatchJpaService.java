package klaicm.backlayer.tennisscores.services.jpadata;

import klaicm.backlayer.tennisscores.model.Match;
import klaicm.backlayer.tennisscores.model.Player;
import klaicm.backlayer.tennisscores.repositories.MatchRepository;
import klaicm.backlayer.tennisscores.services.MatchService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class MatchJpaService implements MatchService {

    @Autowired
    PlayerJpaService playerJpaService;

    private final MatchRepository matchRepository;

    public MatchJpaService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public Set<Match> findAll() {
        Set<Match> matches = new HashSet<>();
        matchRepository.findAll().forEach(matches::add);
        return matches;
    }

    @Override
    public Match findById(Long aLong) {
        return matchRepository.findById(aLong).orElse(null);
    }

    @Override
    public Match save(Match match) {

        playerJpaService.updatePlayer(match);

        return matchRepository.save(match);
    }

    @Override
    public void delete(Match match) {
        matchRepository.delete(match);
    }

    @Override
    public void deleteById(Long id) {
        matchRepository.deleteById(id);
    }

    @Override
    public Set<Match> getPlayerMatches(Long id) {
        return matchRepository.getPlayerMatches(id);
    }

    @Autowired
    MatchJpaService matchJpaService;

    public void importXlsxFile(String fileName) {
        String FILE_NAME = "D:/" + fileName + ".xlsx";
        Set<Player> allPlayers = playerJpaService.findAll();


        try {

            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            int set = 0;
            int rowIndex = 0;
            String result = "";

            Player playerW = new Player();
            Player playerL = new Player();

            while (iterator.hasNext()) {

                rowIndex++;
                int cellIndex = 0;
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                while(cellIterator.hasNext()) {
                    cellIndex++;
                    Cell currentCell = cellIterator.next();
                    if (currentCell.getCellType() == CellType.STRING) {
                        set = 1; // kreće brojanje setova
                        if (cellIndex == 1) {
                            if (rowIndex == 1) {
                                for (Player player : allPlayers) {
                                    if (currentCell.getStringCellValue().equalsIgnoreCase((player.getFirstName() + " " + player.getLastName()))) {
                                        playerW = player;
                                        System.out.println("playerW = " + player);
                                        break;
                                    } else {
                                        playerW = null;
                                    }
                                }
                            } else {
                                Match match = new Match();
                                System.out.println("---------Setiranje meča.---------");
                                System.out.println("playerW = " + playerW.getFirstName() + " " + playerW.getLastName());
                                System.out.println("playerL = " + playerL.getFirstName() + " " + playerL.getLastName());
                                System.out.println("result = " + result);
                                match.setPlayerW(playerW);
                                match.setPlayerL(playerL);
                                match.setResult(result);
                                System.out.println("-------Kraj setiranja meča-----");

                                matchJpaService.save(match);

                                result = "";
                                for (Player player : allPlayers) {
                                    if (currentCell.getStringCellValue().equalsIgnoreCase((player.getFirstName() + " " + player.getLastName()))) {
                                        playerW = player;
                                        break;
                                    } else {
                                        playerW = null;
                                    }
                                }

                            }
                        } else {
                            for (Player player : allPlayers) {
                                if (currentCell.getStringCellValue().equalsIgnoreCase((player.getFirstName() + " " + player.getLastName()))) {
                                    playerL = player;
                                    break;
                                } else {
                                    playerL = null;
                                }
                            }
                        }
                    } else if (currentCell.getCellType() == CellType.NUMERIC) {
                        if (cellIndex == 1 && set == 1) {
                            result = result.concat((Integer.toString((int) currentCell.getNumericCellValue())));
                        } else if (cellIndex == 2 && set == 1) {
                            result = result.concat(":" + (Integer.toString((int) currentCell.getNumericCellValue())));
                            set++;
                        } else if (cellIndex == 1 && set == 2) {
                            result = result.concat(" " + (Integer.toString((int) currentCell.getNumericCellValue())));
                        } else if (cellIndex == 2 && set == 2) {
                            result = result.concat(":" + (Integer.toString((int) currentCell.getNumericCellValue())));
                            set++;
                        } else if (cellIndex == 1 && set == 3) {
                            result = result.concat(" " + (Integer.toString((int) currentCell.getNumericCellValue())));
                        } else if (cellIndex == 2 && set == 3) {
                            result = result.concat(":" + (Integer.toString((int) currentCell.getNumericCellValue())));
                            set++;
                        }
                    }
                }
            }
            System.out.println("end");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();;
        }
    }
}
