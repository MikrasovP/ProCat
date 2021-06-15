package proCat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rent_inventory")
public class RentInventory {
    @Id
    @GeneratedValue
    @Column(name = "inventory_id", nullable = false)
    private UUID inventoryId;
    @Column(name = "inventory_name", nullable = false)
    private String inventoryName;
    @Column(name = "path_to_img")
    private String pathToImg;
    @Column(name = "price_per_hour")
    private BigDecimal pricePerHour;
    @ManyToOne()
    @JoinColumn(name = "type_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private InventoryType inventoryType;
    @ManyToOne()
    @JoinColumn(name = "status_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private AvailabilityStatus availabilityStatus;
    @ManyToOne()
    @JoinColumn(name = "station_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private RentStation rentStation;
    @OneToMany(mappedBy = "rentInventory")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Rent> rentSet;
}
