package pl.edu.agh.zti.model.match;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * Database entity representing match
 */
@Entity
@Data
@Table(name = "matches")
public class Match {
    @Id
    private Long id;

    @Column(nullable = false)
    private Date date;

    private boolean finished = false;
}
