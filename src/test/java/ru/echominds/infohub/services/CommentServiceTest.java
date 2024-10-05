package ru.echominds.infohub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.echominds.infohub.FakeDomain;
import ru.echominds.infohub.convertors.CommentConvertor;
import ru.echominds.infohub.domain.Article;
import ru.echominds.infohub.domain.Comment;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.ArticleDTO;
import ru.echominds.infohub.dtos.CommentDTO;
import ru.echominds.infohub.dtos.UpdatedCommentDto;
import ru.echominds.infohub.dtos.UserDTO;
import ru.echominds.infohub.exceptions.ArticleNotFoundException;
import ru.echominds.infohub.exceptions.CommentNotFoundException;
import ru.echominds.infohub.exceptions.UnauthorizedException;
import ru.echominds.infohub.exceptions.UserNotFoundException;
import ru.echominds.infohub.repositories.ArticleRepository;
import ru.echominds.infohub.repositories.CommentRepository;
import ru.echominds.infohub.repositories.UserRepository;
import ru.echominds.infohub.security.SecurityAuthorizationManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CommentConvertor commentConvertor;

    @Mock
    private SecurityAuthorizationManager securityAuthorizationManager;

    private User fakeUser;
    private Article fakeArticle;
    private Comment fakeComment;
    private CommentDTO fakeCommentDTO;
    private UpdatedCommentDto fakeUpdatedCommentDto;

    @BeforeEach
    void setUp() {
        fakeUser = FakeDomain.createFakeUser();
        fakeArticle = FakeDomain.createFakeArticle(fakeUser);
        fakeComment = FakeDomain.createFakeComment(fakeUser, fakeArticle);
        fakeCommentDTO = FakeDomain.createFakeCommentDTO();
        fakeUpdatedCommentDto = FakeDomain.createFakeUpdatedCommentDTO();
    }

    @Test
    void getCommentById_ShouldReturnComment_WhenAllValid() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(fakeComment));
        when(commentRepository.findAllByReplyTo(1L)).thenReturn(new ArrayList<>());
        when(commentConvertor.convertToDTO(fakeComment, Collections.emptyList())).thenReturn(fakeCommentDTO);

        CommentDTO actualDTO = commentService.getCommentById(1L);

        assertEquals(fakeCommentDTO, actualDTO);
        verify(commentRepository).findById(1L);
        verify(commentConvertor).convertToDTO(fakeComment, Collections.emptyList());
    }

    @Test
    void getCommentById_ShouldReturnException_WhenCommentNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> commentService.getCommentById(1L));

        verify(commentRepository).findById(1L);
    }

    @Test
    void getAllCommentsForArticle_ShouldReturnComments_WhenAllValid() {
        when(commentRepository.findCommentByArticleId(1L, PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(Collections.singletonList(fakeComment)));
        when(commentConvertor.convertToDTO(fakeComment, Collections.emptyList()))
                .thenReturn(fakeCommentDTO);

        List<CommentDTO> comments = commentService.getAllCommentsForArticle(1L,
                PageRequest.of(0, 10));

        assertEquals(fakeCommentDTO, comments.get(0));

        verify(commentRepository).findCommentByArticleId(1L, PageRequest.of(0, 10));
        verify(commentConvertor).convertToDTO(fakeComment, Collections.emptyList());
    }

    @Test
    void createComment_ShouldCreateComment_WhenAllValid() {
        when(securityAuthorizationManager.getCurrentUser()).thenReturn(fakeUser);
        when(userRepository.findById(fakeUser.getId())).thenReturn(Optional.of(fakeUser));
        when(articleRepository.findById(fakeArticle.getId())).thenReturn(Optional.of(fakeArticle));
        when(commentConvertor.convertToDomain(fakeCommentDTO, fakeUser, fakeArticle)).thenReturn(fakeComment);

        commentService.createComment(fakeCommentDTO);

        verify(commentRepository).save(fakeComment);
    }

    @Test
    void createComment_ShouldThrowUnauthorizedException_WhenUserUnauthorized() {
        when(securityAuthorizationManager.getCurrentUser()).thenReturn(null);

        assertThrows(UnauthorizedException.class, () -> commentService.createComment(fakeCommentDTO));

        verify(securityAuthorizationManager).getCurrentUser();
    }

    @Test
    void createComment_ShouldThrowException_WhenArticleNotFound() {
        when(securityAuthorizationManager.getCurrentUser()).thenReturn(fakeUser);
        when(userRepository.findById(fakeUser.getId())).thenReturn(Optional.of(fakeUser));
        when(articleRepository.findById(fakeArticle.getId())).thenReturn(Optional.empty());

        assertThrows(ArticleNotFoundException.class, () -> commentService.createComment(fakeCommentDTO));

        verify(securityAuthorizationManager).getCurrentUser();
        verify(userRepository).findById(fakeUser.getId());
        verify(articleRepository).findById(fakeArticle.getId());
    }

    @Test
    void updateComment_ShouldUpdateComment_WhenAllValid() {
        when(commentRepository.findById(fakeComment.getId())).thenReturn(Optional.of(fakeComment));

        commentService.updateComment(1L, fakeUpdatedCommentDto);

        verify(securityAuthorizationManager).getAccessForComment(fakeComment);
        verify(commentRepository).save(fakeComment);
    }

    @Test
    void deleteComment_ShouldDeleteComment_WhenAllValid() {
        User fakeUser2 = FakeDomain.createFakeUser();
        Article fakeArticle2 = FakeDomain.createFakeArticle(fakeUser2);

        fakeUser2.setComments(new ArrayList<>(List.of(fakeComment)));
        fakeArticle2.setComments(new ArrayList<>(List.of(fakeComment)));

        when(commentRepository.findById(fakeComment.getId())).thenReturn(Optional.of(fakeComment));
        when(userRepository.findById(fakeUser2.getId())).thenReturn(Optional.of(fakeUser2));
        when(articleRepository.findById(fakeArticle2.getId())).thenReturn(Optional.of(fakeArticle2));

        commentService.deleteComment(1L);

        verify(commentRepository).delete(fakeComment);
        verify(userRepository).save(fakeUser2);
        verify(articleRepository).save(fakeArticle2);
    }

    @Test
    void deleteComment_ShouldThrowException_WhenUserNotFound() {
        Article fakeArticle2 = FakeDomain.createFakeArticle(fakeUser);

        fakeArticle2.setComments(new ArrayList<>(List.of(fakeComment)));

        when(commentRepository.findById(fakeComment.getId())).thenReturn(Optional.of(fakeComment));
        when(userRepository.findById(fakeUser.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> commentService.deleteComment(1L));
    }

    @Test
    void deleteComment_ShouldThrowException_WhenArticleNotFound() {
        User fakeUser2 = FakeDomain.createFakeUser();

        fakeUser2.setComments(new ArrayList<>(List.of(fakeComment)));

        when(commentRepository.findById(fakeComment.getId())).thenReturn(Optional.of(fakeComment));
        when(userRepository.findById(fakeUser2.getId())).thenReturn(Optional.of(fakeUser2));
        when(articleRepository.findById(fakeArticle.getId())).thenReturn(Optional.empty());

        assertThrows(ArticleNotFoundException.class, () -> commentService.deleteComment(1L));
    }

}