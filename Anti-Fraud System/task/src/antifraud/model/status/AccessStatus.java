package antifraud.model.status;

import antifraud.enums.Operation;
import antifraud.model.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AccessStatus {
    String status;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Operation operation;

    public AccessStatus(String username, Operation operation){
        this.status = "User "+username+" " + operation.name().toLowerCase()+"ed!";
    }

}
