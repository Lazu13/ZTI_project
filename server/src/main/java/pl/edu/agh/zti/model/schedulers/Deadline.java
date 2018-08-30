package pl.edu.agh.zti.model.schedulers;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Database entity representing deadline
 */
@Entity
@Data
@Table(name = "deadline")
public class Deadline {
    @Id
    private Long id;

    @Column(nullable = false)
    private Date date;
}
