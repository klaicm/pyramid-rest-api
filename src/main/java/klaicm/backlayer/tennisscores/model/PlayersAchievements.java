package klaicm.backlayer.tennisscores.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
public class PlayersAchievements extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "player_id")
    Player player;

    @ManyToOne
    @JoinColumn(name = "achievement_id")
    Achievement achievement;

    Date dateAchieved;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public Date getDateAchieved() {
        return dateAchieved;
    }

    public void setDateAchieved(Date dateAchieved) {
        this.dateAchieved = dateAchieved;
    }
}
