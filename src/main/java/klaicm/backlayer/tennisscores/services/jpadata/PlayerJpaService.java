package klaicm.backlayer.tennisscores.services.jpadata;

import klaicm.backlayer.tennisscores.model.ArchData;
import klaicm.backlayer.tennisscores.model.Match;
import klaicm.backlayer.tennisscores.model.Player;
import klaicm.backlayer.tennisscores.repositories.PlayerRepository;
import klaicm.backlayer.tennisscores.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerJpaService implements PlayerService {

    private static final int K = 32;

    @Autowired
    ArchDataJpaService archDataJpaService;

    @Autowired
    MatchJpaService matchJpaService;

    private final PlayerRepository playerRepository;

    public PlayerJpaService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Set<Player> findAll() {
        Set<Player> players = new HashSet<>();
        playerRepository.findAll().forEach(players::add);
        return players;
    }

    @Override
    public Player findById(Long id) {
        Player player = playerRepository.findById(id).orElse(null);
        Set<ArchData> archData = archDataJpaService.getArchDataByPlayerId(player.getId());

        player.setArchData(archData);

        return player;

    }

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public void delete(Player player) {
        playerRepository.delete(player);
    }

    @Override
    public void deleteById(Long id) {
        playerRepository.deleteById(id);
    }

    public void updatePlayer(Match match) {

        Player playerW = findById(match.getPlayerW().getId());
        Player playerL = findById(match.getPlayerL().getId());

        Map<String, Double> probabilityMap = this.calculateProbabilityOfWin(playerW.getElo(), playerL.getElo());

        // double raUpdated = ra + K*(1 - ea);
        // double rbUpdated = rb + K*(0 - eb);
        int raUpdated = (int) Math.round(playerW.getElo() + K*(1 - probabilityMap.get("ea")));
        int rbUpdated = (int) Math.round(playerL.getElo() + K*(0 - probabilityMap.get("eb")));

        if (match.getResult().length() == 7) {
            playerW.setWinsInTwo(playerW.getWinsInTwo() + 1);
            playerW.setPoints(playerW.getPoints() + 3);
            playerL.setLosesInTwo(playerL.getLosesInTwo() + 1);
        } else if (match.getResult().length() > 7) {
            playerW.setWinsInTb(playerW.getWinsInTb() + 1);
            playerW.setPoints(playerW.getPoints() + 2);
            playerL.setLosesInTb(playerL.getLosesInTb() + 1);
            playerL.setPoints(playerL.getPoints() + 1);
        } else {
            throw new IllegalArgumentException("Uneseni rezultat neispravan");
        }

        playerW.setElo(raUpdated);
        playerL.setElo(rbUpdated);

        this.save(playerW);
        this.save(playerL);

        ArchData playerWArch = insertArchData(playerW, raUpdated, match, true);
        ArchData playerLArch = insertArchData(playerL, rbUpdated, match, false);

        archDataJpaService.save(playerWArch);
        archDataJpaService.save(playerLArch);
    }

    public Map<String, Double> calculateProbabilityOfWin(Integer playerAElo, Integer playerBElo) {
        Map<String, Double> probabilityMap = new HashMap<String, Double>();

        double ra = playerAElo;
        double rb = playerBElo;

        double ea = 1/(1+ Math.pow(10, ((rb-ra)/400)));
        probabilityMap.put("ea", ea);
        double eb = 1/(1+ Math.pow(10, ((ra-rb)/400)));
        probabilityMap.put("eb", eb);

        return probabilityMap;
    }

    private ArchData insertArchData(Player player, int ratingUpdated, Match match, boolean isWinner) {
        ArchData playerArch = new ArchData();
        Set<Player> allPlayersSet = this.findAll();
        List<Player> allPlayersList = allPlayersSet.stream().sorted(Comparator.comparing(Player::getPoints).reversed()).collect(Collectors.toList());

        playerArch.setPlayer(player);
        playerArch.setDate(match.getDate());
        playerArch.setEloRating(ratingUpdated);
        int totalGames = player.getLosesInTb() + player.getLosesInTwo() + player.getWinsInTb() + player.getWinsInTwo();
        double winPercentageW = (((double)player.getWinsInTb() + (double)player.getWinsInTwo())/totalGames)*100;
        playerArch.setWinPercentage((int)Math.round(winPercentageW));

        int position = allPlayersList.indexOf(player) + 1;
        playerArch.setPosition(position);

        if (isWinner) {
            playerArch.setTotalWins((allPlayersList.get(allPlayersList.indexOf(player)).getWinsInTb() +
                    allPlayersList.get(allPlayersList.indexOf(player)).getWinsInTwo()) + 1);
            playerArch.setTotalLoses((allPlayersList.get(allPlayersList.indexOf(player)).getLosesInTb() +
                    allPlayersList.get(allPlayersList.indexOf(player)).getLosesInTwo()));
        } else {
            playerArch.setTotalLoses((allPlayersList.get(allPlayersList.indexOf(player)).getLosesInTb() +
                    allPlayersList.get(allPlayersList.indexOf(player)).getLosesInTwo()) + 1);
            playerArch.setTotalWins((allPlayersList.get(allPlayersList.indexOf(player)).getWinsInTb() +
                    allPlayersList.get(allPlayersList.indexOf(player)).getWinsInTwo()));
        }

        return playerArch;
    }
}
