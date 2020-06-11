package klaicm.backlayer.tennisscores.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Round extends BaseEntity {

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "round")
    Set<Match> matches;

    Integer roundNumber;
    LocalDate dateFrom;
    LocalDate dateTo;

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

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
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
