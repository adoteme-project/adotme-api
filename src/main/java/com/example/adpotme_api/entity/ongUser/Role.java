package com.example.adpotme_api.entity.ongUser;

public enum Role {
    ADMIN("Admin"),
    MODERATOR("Moderador"),
    USER("User");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
