package ru.echominds.infohub;

import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.UserDTO;

public class FakeDomain {

    public static User createFakeUser() {
        User fakeUser = new User();

        fakeUser.setId(1L);
        fakeUser.setName("fake user");
        fakeUser.setEmail("fake@email.com");
        fakeUser.setAvatar("fake avatar");

        return fakeUser;
    }

    public static UserDTO createFakeUserDTO() {
        return new UserDTO(
                "fake user",
                "fake@email.com",
                "fake avatar"
        );
    }
}
