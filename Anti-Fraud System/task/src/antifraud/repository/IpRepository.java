package antifraud.repository;

import antifraud.model.ip.IP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IpRepository extends JpaRepository<IP,Long> {
    boolean existsByIp(String ip);

    Optional<IP> getByIpIgnoreCase(String ip);

    List<IP> getByOrderByIdAsc();




}
