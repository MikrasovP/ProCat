package proCat.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserCurrentRentDto {
    private RentInventoryDTO rentInventoryDTO;
    private BigDecimal payAmount;
}
