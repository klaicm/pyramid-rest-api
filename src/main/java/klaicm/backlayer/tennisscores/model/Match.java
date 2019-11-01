package klaicm.backlayer.tennisscores.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Match extends BaseEntity {

    @ManyToOne
    private Player playerW;

    @ManyToOne
    private Player playerL;

    private String result;
    private Date date;

    public Player getPlayerW() {
        return playerW;
    }

    public void setPlayerW(Player playerW) {
        this.playerW = playerW;
    }

    public Player getPlayerL() {
        return playerL;
    }

    public void setPlayerL(Player playerL) {
        this.playerL = playerL;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
