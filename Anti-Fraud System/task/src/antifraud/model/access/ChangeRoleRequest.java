package antifraud.model.access;

import antifraud.enums.RoleType;
import lombok.Getter;

@Getter
public class ChangeRoleRequest {
    String username;
    RoleType role;
}
