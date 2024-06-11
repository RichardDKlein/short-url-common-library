/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.dto;

/**
 * Class defining a DTO (Data Transfer Object) containing
 * `username` and `role` fields.
 */
public class UsernameAndRoleDto {
    private String username;
    private String role;

    /**
     * General constructor.
     *
     * @param username The username.
     * @param role The user role.
     */
    public UsernameAndRoleDto(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UsernameAndRoleDto{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
