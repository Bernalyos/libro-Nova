
package com.codeup.libronova.domain;

import java.time.LocalDateTime;

/**
 *
 * @author Coder
 */
public class Member {
   

    private int id;
    private String name;
    private boolean active;
    private boolean deleted;
    private Role role;
    private AccessLevel accessLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Role {
        REGULAR, PREMIUM
    }

    public enum AccessLevel {
        READ_ONLY, READ_WRITE, MANAGE
    }

    public Member() {}

    public Member(int id, String name, boolean active, boolean deleted, Role role, AccessLevel accessLevel) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.deleted = deleted;
        this.role = role;
        this.accessLevel = accessLevel;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public AccessLevel getAccessLevel() { return accessLevel; }
    public void setAccessLevel(AccessLevel accessLevel) { this.accessLevel = accessLevel; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Member{id=%s, name='%s', active=%s, deleted=%s, role=%s, accessLevel=%s, createdAt=%s, updatedAt=%s}"
            .formatted(id, name, active, deleted, role, accessLevel, createdAt, updatedAt);
}
}          

