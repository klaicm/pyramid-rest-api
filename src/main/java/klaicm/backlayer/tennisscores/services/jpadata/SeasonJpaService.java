package klaicm.backlayer.tennisscores.services.jpadata;

import klaicm.backlayer.tennisscores.model.Season;
import klaicm.backlayer.tennisscores.repositories.SeasonRepository;
import klaicm.backlayer.tennisscores.services.SeasonService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpaservice")
public class SeasonJpaService implements SeasonService {

    private final SeasonRepository seasonRepository;

    public SeasonJpaService (SeasonRepository seasonRepository) { this.seasonRepository = seasonRepository; }

    @Override
    public Set<Season> findAll() {
        Set<Season> seasons = new HashSet<>();
        seasonRepository.findAll().forEach(seasons::add);
        return seasons;
    }

    @Override
    public Season findById(Long id) {
        return seasonRepository.findById(id).orElse(null);
    }

    @Override
    public Season save(Season season) {
        return seasonRepository.save(season);
    }

    @Override
    public void delete(Season season) {
        seasonRepository.delete(season);

    }

    @Override
    public void deleteById(Long id) {
        seasonRepository.deleteById(id);
    }
}
