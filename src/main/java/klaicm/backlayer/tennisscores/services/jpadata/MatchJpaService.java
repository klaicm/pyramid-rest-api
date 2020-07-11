package klaicm.backlayer.tennisscores.services.jpadata;

import klaicm.backlayer.tennisscores.model.Achievement;
import klaicm.backlayer.tennisscores.model.Match;
import klaicm.backlayer.tennisscores.model.Player;
import klaicm.backlayer.tennisscores.model.PlayersAchievements;
import klaicm.backlayer.tennisscores.repositories.MatchRepository;
import klaicm.backlayer.tennisscores.services.MatchService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpaservice")
public class MatchJpaService implements MatchService {

    @Autowired
    PlayerJpaService playerJpaService;

    @Autowired
    AchievementJpaService achievementJpaService;

    private final MatchRepository matchRepository;

    final static Logger logger = Logger.getLogger(PlayerJpaService.class.getName());

    public MatchJpaService (MatchRepository matchRepository) { this.matchRepository = matchRepository; }

    @Override
    public Set<Match> findAll() {
        Set<Match> matches = new HashSet<>();
        matchRepository.findAll().forEach(matches::add);
        return matches;
    }

    @Override
    public Match findById(Long id) {
        return matchRepository.findById(id).orElse(null);
    }

    public Match scheduleMatch(Match match) {
        return matchRepository.save(match);
    }

    @Override
    public Match save(Match match) {

        if (match.isMatchPlayed()) {
            logger.info("Updating match: " + match.getId());
        } else {
            logger.info("Creating match: " + match.getId());
        }

        Player playerWinner = playerJpaService.findById(match.getPlayerWinner().getId());
        Player playerDefeated = playerJpaService.findById(match.getPlayerDefeated().getId());

        if (playerWinner != null && playerDefeated != null && match.getPlayerRowAttacker() != null && match.getPlayerRowDefender() != null) {

            if (!playerDefeated.getPlayerStats().getCurrentRow().equals(playerWinner.getPlayerStats().getCurrentRow())) {
                if (playerWinner.getPlayerStats().getCurrentRow() != 1 && !playerWinner.getId().equals(match.getPlayerRowDefender().getId())) {
                    playerWinner.getPlayerStats().setCurrentRow(playerWinner.getPlayerStats().getCurrentRow() - 1);
                }

                if (!playerDefeated.getId().equals(match.getPlayerRowAttacker().getId())) {
                    playerDefeated.getPlayerStats().setCurrentRow(playerDefeated.getPlayerStats().getCurrentRow() + 1);
                }
            }

            playerDefeated.getPlayerStats().setCurrentStreak(0);

            if (playerWinner.getPlayerStats().getCurrentRow() < playerWinner.getPlayerStats().getBestRow()) {
                playerWinner.getPlayerStats().setBestRow(playerWinner.getPlayerStats().getBestRow() - 1);
            }

            playerWinner.getPlayerStats().setCurrentStreak(playerWinner.getPlayerStats().getCurrentStreak() + 1);

            if (playerWinner.getPlayerStats().getCurrentStreak() > playerWinner.getPlayerStats().getBestStreak()) {
                playerWinner.getPlayerStats().setBestStreak(playerWinner.getPlayerStats().getBestStreak() + 1);
            }

            // TROPHIES AND ACHIEVEMENTS

            playerWinner.setPlayerAchievements(setNewAchievements(playerWinner, match));
            playerDefeated.setPlayerAchievements(setNewAchievements(playerDefeated, match));
        }

        match.setPlayerWinner(playerWinner);
        match.setPlayerDefeated(playerDefeated);

        return matchRepository.save(match);
    }

    private Set<PlayersAchievements> setNewAchievements (Player player, Match currentMatch) {

        Set<Achievement> allAchievements = achievementJpaService.findAll();
        Set<PlayersAchievements> playersAchievements = player.getPlayerAchievements();
        PlayersAchievements playerAchievement = new PlayersAchievements();

        if (player.getPlayerStats().getCurrentRow() == 1) {

            for (Achievement achievement : allAchievements) {
                if (achievement.getTrophyName().equals("firstRow")) {
                    playerAchievement.setAchievement(achievement);
                    playerAchievement.setPlayer(player);
                }
            }

            playersAchievements.add(playerAchievement);
        }

        if (player.getPlayerStats().getCurrentRow() == 2) {

            for (Achievement achievement : allAchievements) {
                if (achievement.getTrophyName().equals("secondRow")) {
                    playerAchievement.setAchievement(achievement);
                    playerAchievement.setPlayer(player);
                }
            }

            playersAchievements.add(playerAchievement);
        }

        if (player.getPlayerStats().getCurrentRow() == 3) {

            for (Achievement achievement : allAchievements) {
                if (achievement.getTrophyName().equals("thirdRow")) {
                    playerAchievement.setAchievement(achievement);
                    playerAchievement.setPlayer(player);
                }

            }

            playersAchievements.add(playerAchievement);
        }

        if (player.getPlayerStats().getCurrentStreak() == 3) {

            for (Achievement achievement : allAchievements) {
                if (achievement.getTrophyName().equals("threeStreak")) {
                    playerAchievement.setAchievement(achievement);
                    playerAchievement.setPlayer(player);
                }
            }

            playersAchievements.add(playerAchievement);
        }

        if (player.getPlayerStats().getCurrentStreak() == 5) {

            for (Achievement achievement : allAchievements) {
                if (achievement.getTrophyName().equals("fiveStreak")) {
                    playerAchievement.setAchievement(achievement);
                    playerAchievement.setPlayer(player);
                }
            }

            playersAchievements.add(playerAchievement);
        }

        if (player.getPlayerStats().getCurrentStreak() == 10) {

            for (Achievement achievement : allAchievements) {
                if (achievement.getTrophyName().equals("tenStreak")) {
                    playerAchievement.setAchievement(achievement);
                    playerAchievement.setPlayer(player);
                }
            }

            playersAchievements.add(playerAchievement);
        }

        Set<Match> playerMatches = findMatchesByPlayerId((player.getId()));

        // because after this saved match it will be 20
        if (playerMatches.size() == 19) {

            for (Achievement achievement : allAchievements) {
                if (achievement.getTrophyName().equals("twentyPlayed")) {
                    playerAchievement.setAchievement(achievement);
                    playerAchievement.setPlayer(player);
                }
            }

            playersAchievements.add(playerAchievement);
        }

        // because after this saved match it will be 50
        if (playerMatches.size() == 49) {

            for (Achievement achievement : allAchievements) {
                if (achievement.getTrophyName().equals("fiftyPlayed")) {
                    playerAchievement.setAchievement(achievement);
                    playerAchievement.setPlayer(player);
                }
            }

            playersAchievements.add(playerAchievement);
        }

        if (!hasTrophy(playersAchievements, "tenWins")) {
            int i = 0;
            for (Match match : playerMatches) {
                if (match.isMatchPlayed() && (match.getRound().getRoundNumber() != 0)) {
                    if (match.getPlayerWinner().getId().equals(player.getId()))
                        i++;
                    if (i == 9) {
                        for (Achievement achievement : allAchievements) {
                            if (achievement.getTrophyName().equals("tenWins")) {
                                playerAchievement.setAchievement(achievement);
                                playerAchievement.setPlayer(player);
                            }
                        }
                    }
                }
            }
        }

        if (!hasTrophy(playersAchievements, "twentyWins")) {
            int i = 0;
            for (Match match : playerMatches) {
                if (match.isMatchPlayed() && (match.getRound().getRoundNumber() != 0)) {
                    if (match.getPlayerWinner().getId().equals(player.getId()))
                        i++;
                    if (i == 19) {
                        for (Achievement achievement : allAchievements) {
                            if (achievement.getTrophyName().equals("twentyWins")) {
                                playerAchievement.setAchievement(achievement);
                                playerAchievement.setPlayer(player);
                            }
                        }
                    }
                }

            }
        }

        if (!hasTrophy(playersAchievements, "fiftyWins")) {
            int i = 0;
            for (Match match : playerMatches) {
                if (match.isMatchPlayed() && (match.getRound().getRoundNumber() != 0)) {
                    if (match.getPlayerWinner().getId().equals(player.getId()))
                        i++;
                    if (i == 49) {
                        for (Achievement achievement : allAchievements) {
                            if (achievement.getTrophyName().equals("fiftyWins")) {
                                playerAchievement.setAchievement(achievement);
                                playerAchievement.setPlayer(player);
                            }
                        }
                    }
                }
            }
        }

        if (!hasTrophy(playersAchievements, "tenFriendlyW")) {
            int i = 0;
            for (Match match : playerMatches) {
                // round = 0 is friendly
                if (match.isMatchPlayed() && (match.getRound().getRoundNumber() == 0)) {
                    if (match.getPlayerWinner().getId().equals(player.getId()))
                        i++;
                    if (i == 9) {
                        for (Achievement achievement : allAchievements) {
                            if (achievement.getTrophyName().equals("tenFriendlyW")) {
                                playerAchievement.setAchievement(achievement);
                                playerAchievement.setPlayer(player);
                            }
                        }
                    }
                }
            }
        }

        if (!hasTrophy(playersAchievements, "thirtyFriendlyW")) {
            int i = 0;
            for (Match match : playerMatches) {
                // round = 0 is friendly
                if (match.isMatchPlayed() && (match.getRound().getRoundNumber() == 0)) {
                    if (match.getPlayerWinner().getId().equals(player.getId()))
                        i++;
                    if (i == 29) {
                        for (Achievement achievement : allAchievements) {
                            if (achievement.getTrophyName().equals("thirtyFriendlyW")) {
                                playerAchievement.setAchievement(achievement);
                                playerAchievement.setPlayer(player);
                            }
                        }
                    }
                }
            }
        }

        if (!hasTrophy(playersAchievements, "fiftyFriendlyW")) {
            int i = 0;
            for (Match match : playerMatches) {
                // round = 0 is friendly
                if (match.isMatchPlayed() && (match.getRound().getRoundNumber() == 0)) {
                    if (match.getPlayerWinner().getId().equals(player.getId()))
                        i++;
                    if (i == 49) {
                        for (Achievement achievement : allAchievements) {
                            if (achievement.getTrophyName().equals("fiftyFriendlyW")) {
                                playerAchievement.setAchievement(achievement);
                                playerAchievement.setPlayer(player);
                            }
                        }
                    }
                }
            }
        }

        if (currentMatch.getPlayerWinner().getId().equals(player.getId())) {
            if (currentMatch.isMatchPlayed() && currentMatch.getRound().getRoundNumber() != 0) {
                if ((currentMatch.getSetFirst().equalsIgnoreCase("6:0")
                        && currentMatch.getSetSecond().equalsIgnoreCase("6:0"))
                        || (currentMatch.getSetFirst().equalsIgnoreCase("0:6")
                        && currentMatch.getSetSecond().equalsIgnoreCase("0:6"))) {
                    for (Achievement achievement : allAchievements) {
                        if (achievement.getTrophyName().equals("twoBagelsWin")) {
                            playerAchievement.setAchievement(achievement);
                            playerAchievement.setPlayer(player);
                        }
                    }
                } else if ((currentMatch.getSetFirst().equalsIgnoreCase("7:6")
                        && currentMatch.getSetSecond().equalsIgnoreCase("7:6"))
                        || (currentMatch.getSetFirst().equalsIgnoreCase("6:7")
                        && currentMatch.getSetSecond().equalsIgnoreCase("6:7"))) {
                    for (Achievement achievement : allAchievements) {
                        if (achievement.getTrophyName().equals("sevenSixWin")) {
                            playerAchievement.setAchievement(achievement);
                            playerAchievement.setPlayer(player);
                        }
                    }
                }
            }
        }

        return playersAchievements;
    }

    private boolean hasTrophy(Set<PlayersAchievements> playersAchievements, String trophyName) {

        for (PlayersAchievements playerAchievement1 : playersAchievements) {
            if (playerAchievement1.getAchievement().getTrophyName().equals(trophyName))
                return true;
        }

        return false;
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
    public Set<Match> findMatchesByPlayerId(Long id) {
        Set<Match> matches = new HashSet<>();
        matchRepository.findMatchesByPlayerId(id).forEach(matches::add);
        return matches;
    }

    @Override
    public Set<Match> findMatchesByRoundId(Long id) {
        Set<Match> matches = new HashSet<>();
        matchRepository.findMatchesByRoundId(id).forEach(matches::add);
        return matches;
    }

}
