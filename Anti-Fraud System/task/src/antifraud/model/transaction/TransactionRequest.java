package antifraud.model.transaction;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class TransactionRequest {
    @Positive
    @NotNull
    Long amount;

}
