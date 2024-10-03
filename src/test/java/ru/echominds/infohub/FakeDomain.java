package ru.echominds.infohub;

import ru.echominds.infohub.domain.Article;
import ru.echominds.infohub.domain.Comment;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.CommentDTO;
import ru.echominds.infohub.dtos.UserDTO;

import java.util.ArrayList;

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
                1L,
                "fake user",
                "fake@email.com",
                "fake avatar"
        );
    }

    public static Comment createFakeComment(User user, Article article) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setArticle(article);
        comment.setText("random text for comment");
        comment.setRating(100L);
        comment.setReplyTo(null);
        return comment;
    }

    public static CommentDTO createFakeCommentDTO() {
        return new CommentDTO(
                1L,
                1L,
                1L,
                null,
                11L,
                "HELLO MY FRIENDS! I'M FROM INDIA!! INDIA!!",
                new ArrayList<>()
        );
    }
    
}
