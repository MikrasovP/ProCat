package proCat.dto;

import lombok.Data;

@Data
public class RentStationDTO {
    private Long stationId;
    private String stationName;
    private String address;
    private Double latiitude;
    private Double longitude;
}
