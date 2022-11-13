package antifraud.model.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CardDeleteStatus {
    String status;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String number;

    public CardDeleteStatus(String number) {
        this.status = "Card " + number + " successfully removed!";
    }
}
