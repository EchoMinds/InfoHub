package ru.echominds.infohub.domain.role;

import java.util.HashSet;
import java.util.Set;

public enum CommunityRolesType implements Role {
    HEAD_MODERATOR,
    MODERATOR,
    REPORTER;

    private final Set<Role> children = new HashSet<Role>();

    static {
        HEAD_MODERATOR.children.add(MODERATOR);
        MODERATOR.children.add(REPORTER);
    }

    @Override
    public boolean includes(Role role) {
        return this.equals(role) || children.stream().anyMatch(r -> r.includes(role));
    }
}
