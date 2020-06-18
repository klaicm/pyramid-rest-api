package klaicm.backlayer.tennisscores.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
public class Round extends BaseEntity {

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "round")
    Set<Match> matches;

    Integer roundNumber;
    Date dateFrom;
    Date dateTo;

    String roundDescription;

    @ManyToOne
    @JoinColumn(name="season_id")
    Season season;

    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getRoundDescription() {
        return roundDescription;
    }

    public void setRoundDescription(String roundDescription) {
        this.roundDescription = roundDescription;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
