package klaicm.backlayer.tennisscores.repositories;

import klaicm.backlayer.tennisscores.model.PlayerStats;
import org.springframework.data.repository.CrudRepository;

public interface PlayerStatsRepository extends CrudRepository<PlayerStats, Long> {
}
