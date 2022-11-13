package antifraud.service;

import antifraud.enums.TransactionType;
import antifraud.model.transaction.TransactionRequest;
import antifraud.model.transaction.TransactionResponse;
import antifraud.repository.CardRepository;
import antifraud.repository.IpRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {

    IpRepository ipRepository;
    CardRepository cardRepository;

    public TransactionResponse checkTransaction(TransactionRequest request) {
        List<String> listOfInfo = new ArrayList<>();

        if (ipRepository.existsByIp(request.getIp())) {
            listOfInfo.add("ip");
            if(cardRepository.existsByNumber(request.getNumber())){
                listOfInfo.add("card-number");
                if(request.getAmount() > 1500){
                    listOfInfo.add("amount");
                }
                return new TransactionResponse(TransactionType.PROHIBITED, listOfInfo.stream().sorted().collect(Collectors.joining(", ")));
            }
        }

        if (cardRepository.existsByNumber(request.getNumber())) {
            listOfInfo.add("card-number");
            if(ipRepository.existsByIp(request.getIp())){
                listOfInfo.add("ip");
                if(request.getAmount() > 1500){
                    listOfInfo.add("amount");
                }
                return new TransactionResponse(TransactionType.PROHIBITED, listOfInfo.stream().sorted().collect(Collectors.joining(", ")));
            }
        }
        if (request.getAmount() > 1500 && !(cardRepository.existsByNumber(request.getNumber())) && !(ipRepository.existsByIp(request.getIp()))) {
            listOfInfo.add("amount");
            return new TransactionResponse(TransactionType.PROHIBITED, listOfInfo.stream().sorted().collect(Collectors.joining(", ")));

        } else if (request.getAmount() > 200 && request.getAmount() <= 1500 && !(cardRepository.existsByNumber(request.getNumber())) && !(ipRepository.existsByIp(request.getIp()))) {
            listOfInfo.add("amount");
            return new TransactionResponse(TransactionType.MANUAL_PROCESSING, listOfInfo.stream().sorted().collect(Collectors.joining(", ")));

        } else if (request.getAmount() <= 200 && !(cardRepository.existsByNumber(request.getNumber())) && !(ipRepository.existsByIp(request.getIp()))) {
            listOfInfo.add("none");
            return new TransactionResponse(TransactionType.ALLOWED, listOfInfo.stream().sorted().collect(Collectors.joining(", ")));
        }

        if (ipRepository.existsByIp(request.getIp())) {
            return new TransactionResponse(TransactionType.PROHIBITED, listOfInfo.stream().sorted().collect(Collectors.joining(", ")));
        }

        if (cardRepository.existsByNumber(request.getNumber())) {
            return new TransactionResponse(TransactionType.PROHIBITED, listOfInfo.stream().sorted().collect(Collectors.joining(", ")));
        }
        return new TransactionResponse(TransactionType.ALLOWED, "none");
    }
}
