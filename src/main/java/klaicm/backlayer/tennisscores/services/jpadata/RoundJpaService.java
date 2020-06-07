package klaicm.backlayer.tennisscores.services.jpadata;

import klaicm.backlayer.tennisscores.model.Round;
import klaicm.backlayer.tennisscores.repositories.RoundRepository;
import klaicm.backlayer.tennisscores.services.MatchService;
import klaicm.backlayer.tennisscores.services.RoundService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpaservice")
public class RoundJpaService implements RoundService {

    private final RoundRepository roundRepository;

    public RoundJpaService (RoundRepository roundRepository) { this.roundRepository = roundRepository; }

    @Override
    public Set<Round> findAll() {
        Set<Round> rounds = new HashSet<>();
        roundRepository.findAll().forEach(rounds::add);
        return rounds;
    }

    @Override
    public Round findById(Long id) {
        return roundRepository.findById(id).orElse(null);
    }

    @Override
    public Round save(Round round) {
        return roundRepository.save(round);
    }

    @Override
    public void delete(Round round) {
        roundRepository.delete(round);
    }

    @Override
    public void deleteById(Long id) {
        roundRepository.deleteById(id);
    }
}
