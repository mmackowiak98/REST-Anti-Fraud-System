package antifraud.model.transaction;

import antifraud.enums.TransactionType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    TransactionType result;
    String info;
}
