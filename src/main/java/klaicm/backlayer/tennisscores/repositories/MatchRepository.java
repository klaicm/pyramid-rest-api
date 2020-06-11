package klaicm.backlayer.tennisscores.repositories;

import klaicm.backlayer.tennisscores.model.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

    @Query(value = "select m.*, r.round_description from MATCH m join round r on r.id = m.round_id where PLAYER_ROW_ATTACKER_ID = :playerId or PLAYER_ROW_DEFENDER_ID = :playerId or PLAYER_WINNER_ID = :playerId or PLAYER_DEFEATED_ID = :playerId", nativeQuery = true)
    Set<Match> findMatchesByPlayerId(@Param("playerId") Long playerId);

    @Query(value = "select m.*, r.round_description from MATCH m join round r on r.id = m.round_id where r.id = :roundId", nativeQuery = true)
    Set<Match> findMatchesByRoundId(@Param("roundId") Long roundId);
}
