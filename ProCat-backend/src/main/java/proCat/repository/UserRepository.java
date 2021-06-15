package proCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proCat.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Long countByPhoneNumber(String phoneNumber);

    Optional<User> getByPhoneNumber(String phoneNumber);
    User getUserByPhoneNumber(String phoneNumber);

    User getUserById(Long id);
}
