package klaicm.backlayer.tennisscores.services;

import klaicm.backlayer.tennisscores.model.Match;

import java.util.Set;

public interface MatchService extends CrudService<Match, Long> {

    Set<Match> findMatchesByPlayerId(Long id);

    Set<Match> findMatchesByRoundId(Long id);
}
