package proCat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import proCat.dto.RentInventoryDTO;
import proCat.entity.RentInventory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentInventoryMapper {
    @Mapping(source = "inventoryType.typeId", target = "typeId")
    @Mapping(source = "availabilityStatus.statusId", target = "statusId")
    @Mapping(source = "rentStation.stationId", target = "stationId")
    RentInventoryDTO toRentInventoryDTO(RentInventory rentInventory);

    List<RentInventoryDTO> toRentInventoryDTOs(List<RentInventory> rentInventory);
}
