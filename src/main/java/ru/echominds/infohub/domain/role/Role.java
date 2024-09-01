package ru.echominds.infohub.domain.role;

import java.util.Set;

public interface Role {
    boolean includes(Role role);

    static Set<Role> roots(){
        return Set.of(WebSiteRolesType.HEAD_ADMINISTRATOR);
    }
}
