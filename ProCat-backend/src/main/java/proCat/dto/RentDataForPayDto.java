package proCat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RentDataForPayDto {
    private Long rentId;
    private BigDecimal amountToPay;
}
