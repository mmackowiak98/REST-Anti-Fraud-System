package antifraud.model.transaction;

import antifraud.TransactionType;
import lombok.Value;

@Value
public class TransactionResponse {
    TransactionType result;

}
