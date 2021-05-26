package proCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proCat.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isRegistered(String phoneNumber) {
        return userRepository.countByPhoneNumber(phoneNumber) != 0L;
    }
}
