package proCat.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rent_status")
public class RentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "status_id", nullable = false)
    private Long statusId;
    @Column(name = "status_name", nullable = false)
    private String statusName;
}
