package antifraud.service;

import antifraud.model.card.StolenCard;
import antifraud.repository.CardRepository;
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
public class CardService {
    CardRepository cardRepository;


    public Optional<StolenCard> saveStolenCard(StolenCard card) {
        if(cardRepository.existsByNumber(card.getNumber())){
            return Optional.empty();
        }

        return Optional.of(cardRepository.save(card));
    }

    public void deleteStolenCard(String number) {
        final StolenCard stolenCard = cardRepository.findByNumber(number)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        cardRepository.deleteByNumber(number);
    }

    public List<StolenCard> getAllCard() {
        return cardRepository.findAll();
    }
}
