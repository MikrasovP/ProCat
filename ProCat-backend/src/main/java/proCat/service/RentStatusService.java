package proCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proCat.entity.RentStatus;
import proCat.repository.RentStatusRepository;

import java.util.List;

@Service
public class RentStatusService {
    private final RentStatusRepository rentStatusRepository;

    @Autowired
    public RentStatusService(RentStatusRepository rentStatusRepository) {
        this.rentStatusRepository = rentStatusRepository;
    }

    public List<RentStatus> findAll() {
        return rentStatusRepository.findAll();
    }
}
