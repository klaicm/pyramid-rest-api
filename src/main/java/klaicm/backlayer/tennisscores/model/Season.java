package klaicm.backlayer.tennisscores.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Season extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    Set<Round> rounds;

    String seasonName;
    String seasonTier;
    boolean isCurrentSeason;

    public Set<Round> getRounds() {
        return rounds;
    }

    public void setRounds(Set<Round> rounds) {
        this.rounds = rounds;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public String getSeasonTier() {
        return seasonTier;
    }

    public void setSeasonTier(String seasonTier) {
        this.seasonTier = seasonTier;
    }

    public boolean isCurrentSeason() {
        return isCurrentSeason;
    }

    public void setCurrentSeason(boolean currentSeason) {
        isCurrentSeason = currentSeason;
    }
}
