package proCat.mapper;

import org.mapstruct.Mapper;
import proCat.dto.RentStatusDTO;
import proCat.entity.RentStatus;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentStatusMapper {
    RentStatusDTO toRentStatusDTO(RentStatus rentStatus);

    List<RentStatusDTO> toRentStatusDTOs(List<RentStatus> rentStatus);
}
