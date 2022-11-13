package antifraud.repository;

import antifraud.model.card.StolenCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<StolenCard, Long> {
    boolean existsByNumber(String number);

    Long deleteByNumber(String number);

    Optional<StolenCard> findByNumber(String number);





}
