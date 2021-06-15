package proCat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rent")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private Long rentId;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "final_price")
    private BigDecimal finalPrice;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private User user;
    @ManyToOne()
    @JoinColumn(name = "status_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private RentStatus rentStatus;
    @ManyToOne()
    @JoinColumn(name = "inventory_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private RentInventory rentInventory;
}
