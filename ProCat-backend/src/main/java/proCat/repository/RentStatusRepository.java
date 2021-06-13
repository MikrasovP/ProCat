package proCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proCat.entity.RentStatus;

@Repository
public interface RentStatusRepository extends JpaRepository<RentStatus, Long> {
    RentStatus getRentStatusByStatusId(Long id);
}
