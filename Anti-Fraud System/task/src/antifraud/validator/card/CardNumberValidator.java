package antifraud.validator.card;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Slf4j
public class CardNumberValidator implements ConstraintValidator<CardNumberConstraint, String> {

    @Override
    public void initialize(CardNumberConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cardNumber, ConstraintValidatorContext context) {
        if(cardNumber!=null) {
            if(cardNumber.length()<16){
                return false;
            }
            final String trimmedCardNumber = cardNumber.trim();
            log.info("{},{}", cardNumber, cardValidation(trimmedCardNumber));
                return cardValidation(cardNumber);
            }

        return false;
    }

    private boolean cardValidation(String cardNumber) {
        int[] cardIntArray = new int[cardNumber.length()];

        for (int i = 0; i < cardNumber.length(); i++) {
            char c = cardNumber.charAt(i);
            cardIntArray[i] = Integer.parseInt("" + c);
        }

        for (int i = cardIntArray.length - 2; i >= 0; i = i - 2) {
            int num = cardIntArray[i];
            num = num * 2;
            if (num > 9) {
                num = num % 10 + num / 10;
            }
            cardIntArray[i] = num;
        }

        int sum = sumDigits(cardIntArray);

        return sum % 10 == 0;
    }

    public static int sumDigits(int[] arr) {
        return Arrays.stream(arr).sum();
    }
}
