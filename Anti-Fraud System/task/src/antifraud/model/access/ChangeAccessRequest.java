package antifraud.model.access;

import antifraud.enums.Operation;
import lombok.Getter;

@Getter
public class ChangeAccessRequest {
    String username;
    Operation operation;
}
