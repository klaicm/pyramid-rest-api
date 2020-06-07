package klaicm.backlayer.tennisscores.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class PlayerStats extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "player_id")
    Player player;

    Integer bestRow;
    Integer bestStreak;
    Integer currentRow;
    Integer currentStreak;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getBestRow() {
        return bestRow;
    }

    public void setBestRow(Integer bestRow) {
        this.bestRow = bestRow;
    }

    public Integer getBestStreak() {
        return bestStreak;
    }

    public void setBestStreak(Integer bestStreak) {
        this.bestStreak = bestStreak;
    }

    public Integer getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(Integer currentRow) {
        this.currentRow = currentRow;
    }

    public Integer getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }
}
