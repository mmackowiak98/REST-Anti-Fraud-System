package antifraud.service;

import antifraud.exceptions.UsernameNotFound;
import antifraud.model.Status;
import antifraud.model.user.UserDetailsImpl;
import antifraud.model.user.User;
import antifraud.model.user.UserResponse;
import antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User userByUsername = userRepository.findUserByUsername(username);
        if(userByUsername == null){
            throw new UsernameNotFoundException("username: " + username + " not found.");
        }

        return new UserDetailsImpl(userByUsername);
    }

    @Transactional
    public Optional<User> saveUser(User user){
        final String username = user.getUsername();
        if(userRepository.existsByUsernameIgnoreCase(username)){
            return Optional.empty();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(user));
    }


    @Transactional
    public List<User> findAllWithRequirements() {
        return userRepository.getByOrderByIdAsc();
    }

    @Transactional
    public Status deleteUser(String username) throws UsernameNotFound {
        if(userRepository.existsByUsernameIgnoreCase(username)) {
            userRepository.deleteByUsernameIgnoreCase(username);
            return new Status(username,"Deleted successfully!");
        }
        throw new UsernameNotFound();
    }
}
