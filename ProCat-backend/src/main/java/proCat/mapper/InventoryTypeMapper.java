package proCat.mapper;

import org.mapstruct.Mapper;
import proCat.dto.InventoryTypeDTO;
import proCat.entity.InventoryType;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryTypeMapper {
    InventoryTypeDTO toInventoryTypeDTO(InventoryType inventoryType);

    List<InventoryTypeDTO> toInventoryTypeDTOs(List<InventoryType> inventoryType);
}
