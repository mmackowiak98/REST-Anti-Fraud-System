package antifraud.model.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class IpDeleteStatus {
    String status;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String ip;

    public IpDeleteStatus(String ip) {
        this.status = "IP " + ip + " successfully removed!";
    }
}
