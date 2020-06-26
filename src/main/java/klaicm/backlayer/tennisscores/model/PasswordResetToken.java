package klaicm.backlayer.tennisscores.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Date;

@Entity
public class PasswordResetToken extends BaseEntity {

    private static final int EXPIRATION = 60 * 24;

    private String token;

    @OneToOne(targetEntity = DAOUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private DAOUser user;

    private Date expiryDate;
}
