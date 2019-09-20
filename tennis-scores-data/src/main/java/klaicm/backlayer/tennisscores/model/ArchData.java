package klaicm.backlayer.tennisscores.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class ArchData extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="player_id")
    private Player player;

    private Double eloRating;
    private Double winPercentage;
    private Integer position;
    private LocalDate date;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Double getEloRating() {
        return eloRating;
    }

    public void setEloRating(Double eloRating) {
        this.eloRating = eloRating;
    }

    public Double getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(Double winPercentage) {
        this.winPercentage = winPercentage;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
