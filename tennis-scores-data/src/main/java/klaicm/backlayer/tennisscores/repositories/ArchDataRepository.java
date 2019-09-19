package klaicm.backlayer.tennisscores.repositories;

import klaicm.backlayer.tennisscores.model.ArchData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchDataRepository extends CrudRepository<ArchData, Long> {
}
