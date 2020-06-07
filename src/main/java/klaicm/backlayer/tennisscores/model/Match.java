package klaicm.backlayer.tennisscores.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "match")
public class Match extends BaseEntity {

    @ManyToOne
    Player playerWinner;

    @ManyToOne
    Player playerDefeated;

    @ManyToOne
    Player playerRowDefender;

    @ManyToOne
    Player playerRowAttacker;

    Integer challengerRow;
    Integer defenderRow;
    String setFirst;
    String setSecond;
    String setThird;
    boolean isMatchPlayed;
    LocalDate matchDate;

    @ManyToOne
    @JoinColumn(name = "round_id")
    Round round;


    public void setPlayerWinner(Player playerWinner) {
        this.playerWinner = playerWinner;
    }

    public void setPlayerDefeated(Player playerDefeated) {
        this.playerDefeated = playerDefeated;
    }

    public void setPlayerRowDefender(Player playerRowDefender) {
        this.playerRowDefender = playerRowDefender;
    }

    public void setPlayerRowAttacker(Player playerRowAttacker) {
        this.playerRowAttacker = playerRowAttacker;
    }

    public Player getPlayerWinner() {
        return playerWinner;
    }

    public Player getPlayerDefeated() {
        return playerDefeated;
    }

    public Player getPlayerRowDefender() {
        return playerRowDefender;
    }

    public Player getPlayerRowAttacker() {
        return playerRowAttacker;
    }

    public Round getRound() {
        return round;
    }

    public Integer getChallengerRow() {
        return challengerRow;
    }

    public void setChallengerRow(Integer challengerRow) {
        this.challengerRow = challengerRow;
    }

    public Integer getDefenderRow() {
        return defenderRow;
    }

    public void setDefenderRow(Integer defenderRow) {
        this.defenderRow = defenderRow;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public String getSetFirst() {
        return setFirst;
    }

    public void setSetFirst(String setFirst) {
        this.setFirst = setFirst;
    }

    public String getSetSecond() {
        return setSecond;
    }

    public void setSetSecond(String setSecond) {
        this.setSecond = setSecond;
    }

    public String getSetThird() {
        return setThird;
    }

    public void setSetThird(String setThird) {
        this.setThird = setThird;
    }

    public boolean isMatchPlayed() {
        return isMatchPlayed;
    }

    public void setMatchPlayed(boolean matchPlayed) {
        isMatchPlayed = matchPlayed;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }
}
