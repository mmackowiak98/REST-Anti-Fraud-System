package antifraud.service;

import antifraud.model.ip.IP;
import antifraud.repository.IpRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class IpService {
    IpRepository ipRepository;


    public Optional<IP> saveIp(IP ip) {
        if(ipRepository.existsByIp(ip.getIp())){
            return Optional.empty();
        }
        return Optional.of( ipRepository.save(ip));
    }

    public void deleteIp(String ip) {
        final IP ipToDelete = ipRepository.getByIpIgnoreCase(ip)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ipRepository.delete(ipToDelete);
    }

    public List<IP> getAll() {
        return ipRepository.getByOrderByIdAsc();
    }
}
