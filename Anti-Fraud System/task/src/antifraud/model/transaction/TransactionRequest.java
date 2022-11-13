package antifraud.model.transaction;


import antifraud.validator.card.CardNumberConstraint;
import antifraud.validator.ip.IpAddressConstraint;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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

}
