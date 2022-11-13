package antifraud.model.transaction;

import antifraud.enums.TransactionType;
import lombok.Value;

@Value
public class TransactionResponse {
    TransactionType result;

}
