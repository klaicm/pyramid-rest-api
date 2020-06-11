package klaicm.backlayer.tennisscores.services.jpadata;

import klaicm.backlayer.tennisscores.model.Achievement;
import klaicm.backlayer.tennisscores.repositories.AchievementRepository;
import klaicm.backlayer.tennisscores.services.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpaservice")
public class AchievementJpaService implements AchievementService {

    @Autowired
    AchievementJpaService achievementJpaService;

    private final AchievementRepository achievementRepository;

    public AchievementJpaService(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public Set<Achievement> findAll() {
        Set<Achievement> achievements = new HashSet<>();
        achievementRepository.findAll().forEach(achievements::add);
        return achievements;
    }

    @Override
    public Achievement findById(Long id) {
        return achievementRepository.findById(id).orElse(null);
    }

    @Override
    public Achievement save(Achievement achievement) {
        return achievementRepository.save(achievement);
    }

    @Override
    public void delete(Achievement achievement) {
        achievementRepository.delete(achievement);
    }

    @Override
    public void deleteById(Long id) {
        achievementRepository.deleteById(id);
    }
}
