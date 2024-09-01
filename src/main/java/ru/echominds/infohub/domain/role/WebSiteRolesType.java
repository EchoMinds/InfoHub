package ru.echominds.infohub.domain.role;

import java.util.HashSet;
import java.util.Set;

public enum WebSiteRolesType implements Role{
    HEAD_ADMINISTRATOR,
    ADMINISTRATOR,
    USER;

    private final Set<Role> children = new HashSet<Role>();

    static {
        HEAD_ADMINISTRATOR.children.add(ADMINISTRATOR);
        ADMINISTRATOR.children.add(USER);
        ADMINISTRATOR.children.add(CommunityRolesType.HEAD_MODERATOR);
    }

    @Override
    public boolean includes(Role role) {
        return this.equals(role) || children.stream().anyMatch(r -> r.includes(role));
    }
}
