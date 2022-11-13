package antifraud.controller;


import antifraud.exceptions.UsernameNotFound;
import antifraud.model.access.ChangeAccessRequest;
import antifraud.model.status.AccessStatus;
import antifraud.model.status.DeleteStatus;
import antifraud.model.access.ChangeRoleRequest;
import antifraud.model.user.User;
import antifraud.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public User registerUser(@Valid @RequestBody User user) {
        return userDetailsService.saveUser(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','SUPPORT')")
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userDetailsService.findAllWithRequirements();
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping({"/user/{username}", "/user"})
    @ResponseStatus(HttpStatus.OK)
    public DeleteStatus deleteUser(@PathVariable(required = false) String username) throws UsernameNotFound {
        return userDetailsService.deleteUser(username);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/role")
    @ResponseStatus(HttpStatus.OK)
    public User setUserRole(@Valid @RequestBody ChangeRoleRequest request) {
        return userDetailsService.changeUserRole(request.getUsername(), request.getRole());
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/access")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity setUserAccess(@Valid @RequestBody ChangeAccessRequest request) {
        userDetailsService.changeAccountAccess(request.getUsername(), request.getOperation());
        return ResponseEntity.ok(new AccessStatus(request.getUsername(),request.getOperation()));
    }

}
