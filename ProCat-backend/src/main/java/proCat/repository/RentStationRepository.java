package proCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proCat.entity.RentStation;

@Repository
public interface RentStationRepository extends JpaRepository<RentStation, Long> {
}
