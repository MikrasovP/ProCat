package proCat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rent_inventory")
public class RentInventory {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
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
    private InventoryType inventoryType;
    @ManyToOne()
    @JoinColumn(name = "status_id")
    @JsonIgnore
    private AvailabilityStatus availabilityStatus;
    @ManyToOne()
    @JoinColumn(name = "station_id")
    @JsonIgnore
    private RentStation rentStation;
    @OneToMany(mappedBy = "rentInventory")
    @JsonIgnore
    private Set<Rent> rentSet;
}
