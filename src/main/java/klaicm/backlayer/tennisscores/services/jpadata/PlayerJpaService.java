package klaicm.backlayer.tennisscores.services.jpadata;

import klaicm.backlayer.tennisscores.model.Match;
import klaicm.backlayer.tennisscores.model.Player;
import klaicm.backlayer.tennisscores.repositories.PlayerRepository;
import klaicm.backlayer.tennisscores.services.PlayerService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile("jpaservice")
public class PlayerJpaService implements PlayerService {

    private final PlayerRepository playerRepository;

    final static Logger logger = Logger.getLogger(PlayerJpaService.class.getName());

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
        logger.info("Fetched player: " + player.getFirstName() + " "  + player.getLastName());
        return player;
    }

    @Override
    public Player save(Player player) {
        logger.info("Saving player: " + player.getFirstName() + " " + player.getLastName());
        return playerRepository.save(player);
    }

    @Override
    public void delete(Player player) {

        player.setIsActive(player.isActive());

        // potrebno obrisati i red piramide? Najbolje da...
        playerRepository.save(player);
    }

    @Override
    public void deleteById(Long id) {
        playerRepository.deleteById(id);
    }

    public void updatePlayer(Match match) {

    }
}
