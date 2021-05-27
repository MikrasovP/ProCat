package proCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proCat.entity.Rent;

@Repository
public interface RentRepository extends JpaRepository<Rent,Long> {
}
