package proCat.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "inventory_type")
public class InventoryType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "type_id", nullable = false)
    private Long typeId;
    @Column(name = "type_name", nullable = false)
    private String typeName;
    @OneToMany(mappedBy = "inventoryType")
    private Set<RentInventory> inventorySet;
}
