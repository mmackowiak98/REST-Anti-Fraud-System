package antifraud.model.ip;

import antifraud.validator.ip.IpAddressConstraint;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
@Entity
@Getter
@NoArgsConstructor
public class IP {
    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;
    @NotEmpty
    @IpAddressConstraint
    String ip;
}
