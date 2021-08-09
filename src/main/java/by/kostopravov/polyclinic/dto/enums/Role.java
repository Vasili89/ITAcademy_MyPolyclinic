package by.kostopravov.polyclinic.dto.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    ADMIN(Set.of(Permission.READ, Permission.BROWSE, Permission.EDIT)),
    PATIENT(Set.of(Permission.READ)),
    DOCTOR(Set.of(Permission.READ, Permission.BROWSE, Permission.WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
