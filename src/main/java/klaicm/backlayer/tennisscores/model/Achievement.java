package klaicm.backlayer.tennisscores.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Achievement extends BaseEntity {

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "achievement")
    Set<PlayersAchievements> playersAchievements;

    String trophyName;
    String trophyDescription;

    public Set<PlayersAchievements> getPlayersAchievements() {
        return playersAchievements;
    }

    public void setPlayersAchievements(Set<PlayersAchievements> playersAchievements) {
        this.playersAchievements = playersAchievements;
    }

    public String getTrophyName() {
        return trophyName;
    }

    public void setTrophyName(String trophyName) {
        this.trophyName = trophyName;
    }

    public String getTrophyDescription() {
        return trophyDescription;
    }

    public void setTrophyDescription(String trophyDescription) {
        this.trophyDescription = trophyDescription;
    }
}
