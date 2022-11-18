package antifraud.repository;

import antifraud.model.ip.IP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IpRepository extends JpaRepository<IP,Long> {
    boolean existsByIp(String ip);

    Optional<IP> getByIpIgnoreCase(String ip);

    List<IP> getByOrderByIdAsc();




}
