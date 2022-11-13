package antifraud.controller;

import antifraud.enums.TransactionType;
import antifraud.model.transaction.TransactionRequest;
import antifraud.model.transaction.TransactionResponse;
import antifraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/antifraud")
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @PreAuthorize("hasRole('MERCHANT')")
    @PostMapping("/transaction")
    public TransactionResponse getTransactionType(@Valid @RequestBody TransactionRequest t) {
        final TransactionType transactionType = transactionService.checkTransaction(t);
        return new TransactionResponse(transactionType);
    }


}
