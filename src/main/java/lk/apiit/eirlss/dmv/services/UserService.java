package lk.apiit.eirlss.dmv.services;

import lk.apiit.eirlss.dmv.exceptions.CustomException;
import lk.apiit.eirlss.dmv.models.User;
import lk.apiit.eirlss.dmv.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User registerUser(User userDTO) {
        User user = getUserByUsername(userDTO.getUsername());
        if (user != null) throw new CustomException("Email address already exist", HttpStatus.BAD_REQUEST);
        userDTO.setPassword(hashPassword(userDTO.getPassword()));
        return userRepository.save(userDTO);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
