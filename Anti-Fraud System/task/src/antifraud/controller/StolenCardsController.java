package antifraud.controller;

import antifraud.model.card.StolenCard;
import antifraud.model.status.CardDeleteStatus;
import antifraud.service.CardService;
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
public class StolenCardsController {

    CardService cardService;

    @PreAuthorize("hasRole('SUPPORT')")
    @PostMapping("/stolencard")
    @ResponseStatus(HttpStatus.OK)
    public StolenCard saveCard(@Valid @RequestBody StolenCard card){
        return cardService.saveStolenCard(card)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
    }
    @PreAuthorize("hasRole('SUPPORT')")
    @DeleteMapping({"/stolencard/{number}", "/stolencard"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deleteCard(@PathVariable(required = false) @Valid String number){
        cardService.deleteStolenCard(number);
        return ResponseEntity.ok(new CardDeleteStatus(number));
    }
    @PreAuthorize("hasRole('SUPPORT')")
    @GetMapping("/stolencard")
    @ResponseStatus(HttpStatus.OK)
    public List<StolenCard> getStolenCards(){
        return cardService.getAllCard();
    }
}
