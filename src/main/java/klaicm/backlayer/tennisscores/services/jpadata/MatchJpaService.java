package klaicm.backlayer.tennisscores.services.jpadata;

import klaicm.backlayer.tennisscores.model.Match;
import klaicm.backlayer.tennisscores.model.Player;
import klaicm.backlayer.tennisscores.repositories.MatchRepository;
import klaicm.backlayer.tennisscores.services.MatchService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    /**
     * Method for importing .xlsx file with match data and saving them in database.
     * This method of saving matches is used when match data is scrapped from 2Bagels.com
     * For now, file must be on local machine and have specific path.
     *
     * @param fileName Name of the file in local machine (only name, without path and file extension f.e. .xlsx)
     */
    public void importXlsxFile(String fileName) {
        String FILE_NAME = "D:/Workspaces/prince_matches/results/" + fileName + ".xlsx";
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
            Date matchDate = new Date();
            boolean isDateRow = true;

            while (iterator.hasNext()) {
                rowIndex++;
                int cellIndex = 0;
                Row currentRow = iterator.next();
                for (Cell cell : currentRow) {
                    cellIndex++;
                    // If cell is string there are two possibilities -> date or name
                    if (cell.getCellType() == CellType.STRING) {
                        set = 1; // Reset counting set number
                        if (cellIndex == 1) {
                            if (rowIndex == 1) {
                                // First row in excel file is always date
                                try {
                                    matchDate = new SimpleDateFormat("dd.MM.yyyy").parse(cell.getStringCellValue().substring(0, 10) + ".");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                if (isDateRow) {
                                    try {
                                        if (cell.getStringCellValue().length() >= 10) {
                                            matchDate = new SimpleDateFormat("dd.MM.yyyy").parse(cell.getStringCellValue().substring(0, 10) + ".");
                                        }
                                    } catch (ParseException ex) {
                                        ex.printStackTrace();
                                    }
                                } else {
                                    // Second row in excel file are always players
                                    if (rowIndex == 2) {
                                        for (Player player : allPlayers) {
                                            if (cell.getStringCellValue().equalsIgnoreCase((player.getFirstName() + " " + player.getLastName()))) {
                                                playerW = player;
                                                break;
                                            } else {
                                                playerW = null;
                                            }
                                        }
                                    } else {
                                        // Save match and start from next two players. Date is already set in upper logic
                                        saveMatch(playerW, playerL, result, matchDate);

                                        result = "";
                                        for (Player player : allPlayers) {
                                            if (cell.getStringCellValue().equalsIgnoreCase((player.getFirstName() + " " + player.getLastName()))) {
                                                playerW = player;
                                                break;
                                            } else {
                                                playerW = null;
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (isDateRow) {
                                try {
                                    if (cell.getStringCellValue().length() >= 10) {
                                        matchDate = new SimpleDateFormat("dd.MM.yyyy").parse(cell.getStringCellValue().substring(0, 10) + ".");
                                        isDateRow = false;
                                    }
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                for (Player player : allPlayers) {
                                    if (cell.getStringCellValue().equalsIgnoreCase((player.getFirstName() + " " + player.getLastName()))) {
                                        playerL = player;
                                        break;
                                    } else {
                                        playerL = null;
                                    }
                                }
                            }
                        }
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        if (cellIndex == 1 && set == 1) {
                            result = result.concat((Integer.toString((int) cell.getNumericCellValue())));
                        } else if (cellIndex == 2 && set == 1) {
                            result = result.concat(":" + ((int) cell.getNumericCellValue()));
                            set++;
                            isDateRow = true;
                        } else if (cellIndex == 1 && set == 2) {
                            result = result.concat(" " + ((int) cell.getNumericCellValue()));
                        } else if (cellIndex == 2 && set == 2) {
                            result = result.concat(":" + ((int) cell.getNumericCellValue()));
                            set++;
                            isDateRow = true;
                        } else if (cellIndex == 1 && set == 3) {
                            result = result.concat(" " + ((int) cell.getNumericCellValue()));
                        } else if (cellIndex == 2 && set == 3) {
                            result = result.concat(":" + ((int) cell.getNumericCellValue()));
                            set++;
                            isDateRow = true;
                        }
                    }
                }
            }
            saveMatch(playerW, playerL, result, matchDate);

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for final save of the match created from import of .xlsx file
     *
     * @param playerW   Player won
     * @param playerL   Player lost
     * @param result    Match result
     * @param matchDate Match date
     */
    private void saveMatch(Player playerW, Player playerL, String result, Date matchDate) {
        if (playerW != null && playerL != null) {
            Match match = new Match();
            match.setPlayerW(playerW);
            match.setPlayerL(playerL);
            match.setResult(result);
            match.setDate(matchDate);

            save(match);
        }
    }
}
