package antifraud.model;

import antifraud.TransactionType;
import lombok.Value;

@Value
public class TransactionResponse {
    TransactionType result;

}
