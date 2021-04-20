package proCat.mapper;

import org.mapstruct.Mapper;
import proCat.dto.RentStationDTO;
import proCat.entity.RentStation;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentStationMapper {
    RentStationDTO toRentStationDTO(RentStation rentStation);
    List<RentStationDTO> toRentStationDTOs(List<RentStation> rentStation);

}
