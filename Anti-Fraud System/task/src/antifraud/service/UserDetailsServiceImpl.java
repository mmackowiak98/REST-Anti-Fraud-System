package antifraud.service;

import antifraud.enums.Operation;
import antifraud.enums.RoleType;
import antifraud.exceptions.UsernameNotFound;
import antifraud.model.status.DeleteStatus;
import antifraud.model.user.User;
import antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
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

        return userRepository.findUserByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @Transactional
    public Optional<User> saveUser(User user){
        final String username = user.getUsername();
        if(userRepository.existsByUsernameIgnoreCase(username)){
            return Optional.empty();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(userRepository.getByOrderByIdAsc().isEmpty()){
            user.setRole(RoleType.ADMINISTRATOR).setAccountNotLocked(Operation.UNLOCK);
        } else {
            user.setRole(RoleType.MERCHANT).setAccountNotLocked(Operation.LOCK);

        }
        return Optional.of(userRepository.save(user));
    }


    @Transactional
    public List<User> findAllWithRequirements() {
        return userRepository.getByOrderByIdAsc();
    }

    @Transactional
    public DeleteStatus deleteUser(String username) throws UsernameNotFound {
        if(userRepository.existsByUsernameIgnoreCase(username)) {
            userRepository.deleteByUsernameIgnoreCase(username);
            return new DeleteStatus(username,"Deleted successfully!");
        }
        throw new UsernameNotFound();
    }

    @Transactional
    public User changeUserRole(String username, RoleType role) {
        final User user = userRepository.findUserByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(role == RoleType.ADMINISTRATOR){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(user.getRole() == role){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        user.setRole(role);
        return userRepository.save(user);
    }

    @Transactional
    public void changeAccountAccess(String username, Operation operation) {
        final User user = userRepository.findUserByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(user.getRole() == RoleType.ADMINISTRATOR){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        user.setAccountNotLocked(operation);
        userRepository.save(user);
    }
}
