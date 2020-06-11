package klaicm.backlayer.tennisscores.services.jpadata;

import klaicm.backlayer.tennisscores.model.PlayerStats;
import klaicm.backlayer.tennisscores.repositories.PlayerStatsRepository;
import klaicm.backlayer.tennisscores.services.PlayerStatsService;

import java.util.HashSet;
import java.util.Set;

public class PlayerStatsJpaService implements PlayerStatsService {

    private final PlayerStatsRepository playerStatsRepository;

    public PlayerStatsJpaService(PlayerStatsRepository playerStatsRepository) {
        this.playerStatsRepository = playerStatsRepository;
    }

    @Override
    public Set<PlayerStats> findAll() {
        Set<PlayerStats> playerStats = new HashSet<>();
        playerStatsRepository.findAll().forEach(playerStats::add);
        return playerStats;
    }

    @Override
    public PlayerStats findById(Long id) {
        PlayerStats playerStats = playerStatsRepository.findById(id).orElse(null);
        return playerStats;
    }

    @Override
    public PlayerStats save(PlayerStats playerStats) {
        return playerStatsRepository.save(playerStats);
    }

    @Override
    public void delete(PlayerStats playerStats) {
        playerStatsRepository.delete(playerStats);
    }

    @Override
    public void deleteById(Long id) {
        playerStatsRepository.deleteById(id);
    }
}
