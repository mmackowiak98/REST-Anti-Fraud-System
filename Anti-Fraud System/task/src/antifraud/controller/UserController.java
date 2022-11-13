package antifraud.controller;


import antifraud.exceptions.UsernameNotFound;
import antifraud.model.Status;
import antifraud.model.user.User;
import antifraud.model.user.UserResponse;
import antifraud.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserController {

    UserDetailsServiceImpl userDetailsService;


    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@Valid @RequestBody User user){
        return userDetailsService.saveUser(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers(){
        return userDetailsService.findAllWithRequirements();
    }

    @DeleteMapping("/user/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Status deleteUser(@PathVariable("username") String username) throws UsernameNotFound {
        return userDetailsService.deleteUser(username);
    }

}
