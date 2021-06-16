package proCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proCat.entity.Rent;

import java.util.UUID;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    @Query(value = "select case when count(r) > 0 then true else false end from rent r where r.user_id=:user_id and inventory_id=:inv_id and status_id in(1,2)", nativeQuery = true)
    boolean isInventoryRentedByUser(@Param("user_id") Long userId,
                                    @Param("inv_id") UUID inventoryId);

    @Query(value = "select r.* from rent r where r.user_id=:user_id and inventory_id=:inv_id and status_id in(1,2) ", nativeQuery = true)
    Rent getCurrentUserRent(@Param("user_id") Long userId,
                            @Param("inv_id") UUID inventoryId);

    Rent getByRentId(Long id);
}
