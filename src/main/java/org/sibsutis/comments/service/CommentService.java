package org.sibsutis.comments.service;

import org.sibsutis.comments.model.Comment;
import org.sibsutis.comments.model.CommentInput;
import org.sibsutis.comments.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(CommentInput input) {
        Comment comment = new Comment();
        comment.setUserId(input.getUserId());
        comment.setResourceId(input.getResourceId());
        comment.setContent(input.getContent());
        comment.setCreatedAt(OffsetDateTime.now());
        return commentRepository.save(comment);
    }

    public Optional<Comment> getCommentById(Integer commentId) {
        return commentRepository.findById(commentId);
    }

    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Comment> getCommentsByResource(Integer resourceId) {
        return commentRepository.findByResourceId(resourceId);
    }

    public List<Comment> getCommentsByUser(Integer userId) {
        return commentRepository.findByUserId(userId);
    }
}
