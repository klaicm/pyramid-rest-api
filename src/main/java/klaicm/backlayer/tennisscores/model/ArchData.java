package klaicm.backlayer.tennisscores.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class ArchData extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="player_id")
    private Player player;

    private Integer eloRating;
    private Integer winPercentage;
    private Integer position;
    private Date date;
    private Integer totalWins;
    private Integer totalLoses;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getEloRating() {
        return eloRating;
    }

    public void setEloRating(Integer eloRating) {
        this.eloRating = eloRating;
    }

    public Integer getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(Integer winPercentage) {
        this.winPercentage = winPercentage;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(Integer totalWins) {
        this.totalWins = totalWins;
    }

    public Integer getTotalLoses() {
        return totalLoses;
    }

    public void setTotalLoses(Integer totalLoses) {
        this.totalLoses = totalLoses;
    }
}
