package proCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proCat.entity.InventoryType;

@Repository
public interface InventoryTypeRepository extends JpaRepository<InventoryType, Long> {
}
