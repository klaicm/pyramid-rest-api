package klaicm.backlayer.tennisscores.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "player")
public class Player extends BaseEntity {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String userMail;
    private String password;
    private boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private Set<PlayersAchievements> playerAchievements;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "player")
    private PlayerStats playerStats;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Match> matches;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<PlayersAchievements> getPlayerAchievements() {
        return playerAchievements;
    }

    public void setPlayerAchievements(Set<PlayersAchievements> playerAchievements) {
        this.playerAchievements = playerAchievements;
    }

    /*
    public Set<Match> getMatches() {
        return matches;
    }
    */

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(PlayerStats playerStats) {
        this.playerStats = playerStats;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


}
