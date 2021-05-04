package proCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proCat.entity.AvailabilityStatus;
@Repository
public interface AvailabilityStatusRepository extends JpaRepository<AvailabilityStatus,Long> {
}
