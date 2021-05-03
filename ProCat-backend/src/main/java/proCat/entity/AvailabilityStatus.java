package proCat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "availability_status")
public class AvailabilityStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "status_id", nullable = false)
    private Long statusId;
    @Column(name = "status_name", nullable = false)
    private String statusName;
    @OneToMany(mappedBy = "availabilityStatus")
    @JsonIgnore
    private Set<RentInventory> inventorySet;
}
