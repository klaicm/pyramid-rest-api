package klaicm.backlayer.tennisscores.repositories;

import klaicm.backlayer.tennisscores.model.Achievement;
import org.springframework.data.repository.CrudRepository;

public interface AchievementRepository extends CrudRepository<Achievement, Long> {
}
