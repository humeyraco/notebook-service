package com.example.notebook.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN(0L, "ADMIN"),
    USER(1L, "USER");

    private final Long roleId;
    private final String roleName;

    UserRole(Long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public static UserRole fromRoleId(Long roleId) {
        for (UserRole role : values()) {
            if (role.getRoleId().equals(roleId)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No matching role for roleId: " + roleId);
    }
}
