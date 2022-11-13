package antifraud.enums;

import org.springframework.security.core.GrantedAuthority;

public enum RoleType implements GrantedAuthority {
    ADMINISTRATOR,SUPPORT,MERCHANT;

    final String roleName = "ROLE_" + name();

    @Override
    public String getAuthority() {
        return roleName;
    }
}
