package antifraud.controller;

import antifraud.model.ip.IP;
import antifraud.model.status.IpDeleteStatus;
import antifraud.service.IpService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/antifraud")
@AllArgsConstructor
public class SuspiciousIpController {

    IpService ipService;

    @PreAuthorize("hasRole('SUPPORT')")
    @PostMapping("/suspicious-ip")
    @ResponseStatus(HttpStatus.OK)
    public IP saveIp(@Valid @RequestBody IP ip){
        return ipService.saveIp(ip)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
    }
    @PreAuthorize("hasRole('SUPPORT')")
    @DeleteMapping({"/suspicious-ip/{ip}","/suspicious-ip"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deleteIp(@PathVariable(required = false) @Valid String ip){
        ipService.deleteIp(ip);
        return ResponseEntity.ok(new IpDeleteStatus(ip));
    }
    @PreAuthorize("hasRole('SUPPORT')")
    @GetMapping("/suspicious-ip")
    @ResponseStatus(HttpStatus.OK)
    public List<IP> getIpList(){
        return ipService.getAll();
    }
}
