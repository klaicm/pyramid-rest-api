package klaicm.backlayer.tennisscores.services.jpadata;

import klaicm.backlayer.tennisscores.model.Match;
import klaicm.backlayer.tennisscores.repositories.MatchRepository;
import klaicm.backlayer.tennisscores.services.MatchService;
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

    private final MatchRepository matchRepository;

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

    @Override
    public Match save(Match match) {

        // update player i update match je
        // playerJpaService.updatePlayer(match);

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
