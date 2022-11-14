package antifraud.model.transaction;


import antifraud.enums.Region;
import antifraud.validator.card.CardNumberConstraint;
import antifraud.validator.ip.IpAddressConstraint;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Data
public class TransactionRequest {
    @Positive
    @NotNull
    Long amount;
    @NotEmpty
    @IpAddressConstraint
    String ip;
    @NotEmpty
    @CardNumberConstraint
    String number;
    @Enumerated(EnumType.STRING)
    Region region;
    @DateTimeFormat(pattern = "yyyy/mm/dd'T'HH:mm")
    LocalDate date;

}
