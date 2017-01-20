package com.audiowave.tverdakhleb.entity;

public enum RoleType {
    GUEST("guest"), USER("user"), ADMIN("admin");

    private String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
