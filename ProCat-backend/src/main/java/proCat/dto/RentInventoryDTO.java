package proCat.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RentInventoryDTO {
    private UUID inventoryId;
    private String inventoryName;
    private String pathToImg;
    private BigDecimal pricePerHour;
    private Long typeId;
    private Long stationId;
    private Long statusId;
}
