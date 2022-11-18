package antifraud.repository;

import antifraud.enums.Region;
import antifraud.model.transaction.TransactionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionRequest,Long> {


    List<TransactionRequest> findByDateBetweenAndNumber(LocalDateTime minusHours, LocalDateTime date, String number);
}
