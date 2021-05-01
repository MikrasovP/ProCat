package proCat.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rent_station")
public class RentStation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "station_id", nullable = false)
    private Long stationId;
    @Column(name = "station_name", nullable = false)
    private String stationName;
    @Column(name = "address", nullable = false, unique = true)
    private String address;
    @Column(name = "latiitude", nullable = false)
    private Double latiitude;
    @Column(name = "longitude", nullable = false)
    private Double longitude;

}
