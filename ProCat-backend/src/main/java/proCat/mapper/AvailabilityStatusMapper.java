package proCat.mapper;

import org.mapstruct.Mapper;
import proCat.dto.AvailabilityStatusDTO;
import proCat.entity.AvailabilityStatus;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvailabilityStatusMapper {
    AvailabilityStatusDTO toAvailabilityStatusDTO(AvailabilityStatus availabilityStatus);

    List<AvailabilityStatusDTO> toAvailabilityStatusDTOs(List<AvailabilityStatus> availabilityStatus);
}
