package by.kostopravov.polyclinic.dto.enums;

public enum Permission {
    READ("read"),
    WRITE("write"),
    BROWSE("browse"),
    EDIT("edit");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}