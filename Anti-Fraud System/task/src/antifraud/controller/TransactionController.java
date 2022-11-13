package antifraud.controller;

import antifraud.TransactionType;
import antifraud.model.TransactionRequest;
import antifraud.model.TransactionResponse;
import antifraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/antifraud")
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @PostMapping("/transaction")
    public TransactionResponse getTransactionType(@Valid @RequestBody TransactionRequest t) {
        final TransactionType transactionType = transactionService.checkTransaction(t);
        return new TransactionResponse(transactionType);
    }


}