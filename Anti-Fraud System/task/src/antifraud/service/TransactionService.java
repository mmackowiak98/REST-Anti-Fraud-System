package antifraud.service;

import antifraud.enums.Region;
import antifraud.enums.TransactionType;
import antifraud.model.transaction.TransactionRequest;
import antifraud.model.transaction.TransactionResponse;
import antifraud.repository.CardRepository;
import antifraud.repository.IpRepository;
import antifraud.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {

    IpRepository ipRepository;
    CardRepository cardRepository;

    TransactionRepository transactionRepository;

    public TransactionResponse checkTransaction(TransactionRequest transactionRequest) {
        List<String> listOfInfo = new ArrayList<>();
        transactionRepository.save(transactionRequest);

        TransactionType type = checkAmount(transactionRequest.getAmount());

        if (type == TransactionType.PROHIBITED) {
            listOfInfo.add("amount");
        }
        if (cardRepository.existsByNumber(transactionRequest.getNumber())) {
            listOfInfo.add("card-number");
            type = TransactionType.PROHIBITED;
        }
        if (ipRepository.existsByIp(transactionRequest.getIp())) {
            listOfInfo.add("ip");
            type = TransactionType.PROHIBITED;
        }

        List<TransactionRequest> lastHourTransaction = transactionRepository.findByDateBetweenAndNumber(
                transactionRequest.getDate()
                        .minusHours(1L), transactionRequest.getDate(), transactionRequest.getNumber());
        long listOfIpCorrelation = getIpCorrelation(transactionRequest, lastHourTransaction);
        long listOfRegionCorrelation = getRegionCorrelation(transactionRequest, lastHourTransaction);

        if (listOfInfo.isEmpty()) {

            if (type == TransactionType.MANUAL_PROCESSING) {
                listOfInfo.add("amount");
                return new TransactionResponse(type, getMessage(listOfInfo));
            } else {
                Optional<TransactionType> resultOptional = getIpAndRegionCorrelation(listOfInfo,
                        listOfIpCorrelation,
                        listOfRegionCorrelation);
                if (resultOptional.isPresent()) {
                    type = resultOptional.get();
                }
                if (listOfInfo.isEmpty()) {
                    listOfInfo.add("none");
                }
                return new TransactionResponse(type, getMessage(listOfInfo));
            }
        } else {
            Optional<TransactionType> resultOptional = getIpAndRegionCorrelation(listOfInfo,
                    listOfIpCorrelation,
                    listOfRegionCorrelation);
            if (resultOptional.isPresent()) {
                type = resultOptional.get();
            }
            return new TransactionResponse(type, getMessage(listOfInfo));
        }
    }

    private static long getIpCorrelation(TransactionRequest transactionRequest, List<TransactionRequest> lastHourTransaction) {
        return lastHourTransaction.stream()
                .map(TransactionRequest::getIp)
                .filter(t -> !t.equals(transactionRequest.getIp()))
                .distinct()
                .count();
    }

    private static long getRegionCorrelation(TransactionRequest transactionRequest, List<TransactionRequest> lastHourTransaction) {
        return lastHourTransaction.stream()
                .map(TransactionRequest::getRegion)
                .filter(region -> Arrays.asList(Region.values())
                        .contains(region))
                .filter(region -> region.compareTo(transactionRequest.getRegion()) != 0)
                .distinct()
                .count();
    }

    private String getMessage(List<String> violationsMessages) {
        return violationsMessages.stream()
                .sorted()
                .collect(Collectors.joining(", "));
    }

    private TransactionType checkAmount(Long amount) {
        if (amount <= 200) {
            return TransactionType.ALLOWED;
        } else if (amount <= 1500) {
            return TransactionType.MANUAL_PROCESSING;
        } else {
            return TransactionType.PROHIBITED;
        }
    }

    private Optional<TransactionType> getIpAndRegionCorrelation(List<String> violationsMessages, long listOfIpCorrelation, long listOfRegionCorrelation) {
        TransactionType typeResult = null;
        if (listOfIpCorrelation == 2) {
            violationsMessages.add("ip-correlation");
            typeResult = TransactionType.MANUAL_PROCESSING;
        }
        if (listOfIpCorrelation > 2) {
            typeResult = TransactionType.PROHIBITED;
            violationsMessages.add("ip-correlation");
        }
        if (listOfRegionCorrelation == 2) {
            violationsMessages.add("region-correlation");
            typeResult = TransactionType.MANUAL_PROCESSING;
        }
        if (listOfRegionCorrelation > 2) {
            typeResult = TransactionType.PROHIBITED;
            violationsMessages.add("region-correlation");
        }
        if (typeResult == null) {
            return Optional.empty();
        } else {
            return Optional.of(typeResult);
        }
    }

}
