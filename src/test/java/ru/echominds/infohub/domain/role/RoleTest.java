package ru.echominds.infohub.domain.role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    // Проверка на бесконечный цикл | infinite loop check
    @Test
    void shouldNotThrowStackOverflowException() {
        final var roots = Role.roots();
        final var existingRoles = Stream.concat(
                stream(CommunityRolesType.values()),
                stream(WebSiteRolesType.values())
        ).toList();

        assertDoesNotThrow(
                () -> {
                    for (Role root : roots) {
                        for (var roleToCheck : existingRoles) {
                            root.includes(roleToCheck);
                        }
                    }
                }
        );
    }

    // проверка Иерархии | hierarchy check
    @ParameterizedTest
    @MethodSource("provideArgs")
    void shouldIncludeOrNotTheGivenRoles(Role root, Set<Role> rolesToCheck, boolean shouldInclude) {
        for (Role role : rolesToCheck) {
            assertEquals(
                    shouldInclude,
                    root.includes(role)
            );
        }
    }

    private static Stream<Arguments> provideArgs() {
        return Stream.of(
                //check hierarchy Head Administrator
                Arguments.arguments(
                        WebSiteRolesType.HEAD_ADMINISTRATOR,

                        Stream.concat(
                                stream(WebSiteRolesType.values()),
                                stream(CommunityRolesType.values())
                        ).collect(Collectors.toSet()),

                        true
                ),
                //check hierarchy Administrator
                Arguments.arguments(
                        WebSiteRolesType.ADMINISTRATOR,

                        Set.of(
                                CommunityRolesType.HEAD_MODERATOR,
                                CommunityRolesType.MODERATOR,
                                CommunityRolesType.REPORTER,
                                WebSiteRolesType.ADMINISTRATOR,
                                WebSiteRolesType.USER
                        ),

                        true

                ),
                //check hierarchy user
                Arguments.arguments(
                        WebSiteRolesType.USER,
                        Set.of(
                                WebSiteRolesType.HEAD_ADMINISTRATOR,
                                WebSiteRolesType.ADMINISTRATOR
                        ),
                        false
                ),

                //check hierarchy HEAD_MODERATOR
                Arguments.arguments(
                        CommunityRolesType.HEAD_MODERATOR,
                        stream(CommunityRolesType.values()).collect(Collectors.toSet()),
                        true
                ),

                Arguments.arguments(
                        CommunityRolesType.MODERATOR,
                        Set.of(CommunityRolesType.MODERATOR, CommunityRolesType.REPORTER),
                        true
                )

        );

    }
}