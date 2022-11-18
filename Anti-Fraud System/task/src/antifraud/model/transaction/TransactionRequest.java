package antifraud.model.transaction;


import antifraud.enums.Region;
import antifraud.validator.card.CardNumberConstraint;
import antifraud.validator.ip.IpAddressConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class TransactionRequest {
    @Id
    @GeneratedValue
    Long id;
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
    @DateTimeFormat(pattern = "yyyy/mm/dd'T'HH:mm:ss")
    LocalDateTime date;

}
