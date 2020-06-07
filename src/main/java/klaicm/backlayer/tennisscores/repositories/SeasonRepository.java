package klaicm.backlayer.tennisscores.repositories;

import klaicm.backlayer.tennisscores.model.Season;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends CrudRepository<Season, Long> {
}
