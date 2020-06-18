package klaicm.backlayer.tennisscores.repositories;

import klaicm.backlayer.tennisscores.model.DAOUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDaoRepository extends CrudRepository<DAOUser, Long> {
    DAOUser findByUsername(String username);
}
