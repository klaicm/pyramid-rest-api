package klaicm.backlayer.tennisscores.repositories;

import klaicm.backlayer.tennisscores.model.Round;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends CrudRepository<Round, Long> {
}
