package proCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proCat.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Long countByPhoneNumber(String phoneNumber);
}
