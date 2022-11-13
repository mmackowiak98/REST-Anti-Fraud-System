package antifraud.service;

import antifraud.enums.TransactionType;
import antifraud.model.transaction.TransactionRequest;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    public TransactionType checkTransaction(TransactionRequest request) {
            if (request.getAmount() <= 200) {
                return TransactionType.ALLOWED;
            } else if (request.getAmount() <= 1500) {
                return TransactionType.MANUAL_PROCESSING;
            } else
                return TransactionType.PROHIBITED;
    }
}
