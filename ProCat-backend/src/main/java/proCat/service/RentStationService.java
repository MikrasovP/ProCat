package proCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proCat.entity.RentStation;
import proCat.repository.RentStationRepository;

import java.util.List;

@Service
public class RentStationService {
    private final RentStationRepository stationRepository;

    @Autowired
    public RentStationService(RentStationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }
    public List<RentStation> findAll(){
        return stationRepository.findAll();
    }
}
