package proCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proCat.entity.RentInventory;

import java.util.List;

@Repository
public interface RentInventoryRepository extends JpaRepository<RentInventory, Long> {
    @Query(value = "select ri.* from rent_inventory ri where station_id=:station_id", nativeQuery = true)
    List<RentInventory> findAllByStationId(@Param("station_id") Long stationId);

    RentInventory getRentInventoryByInventoryId(Long id);
}
