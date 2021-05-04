package proCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proCat.entity.AvailabilityStatus;
import proCat.repository.AvailabilityStatusRepository;

import java.util.List;

@Service
public class AvailabilityStatusService {
    private final AvailabilityStatusRepository statusRepository;

    @Autowired
    public AvailabilityStatusService(AvailabilityStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<AvailabilityStatus> findAll() {
        return statusRepository.findAll();
    }
}
