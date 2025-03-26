package org.sibsutis.comments.api;

import org.sibsutis.comments.model.Comment;
import org.sibsutis.comments.model.CommentInput;
import org.sibsutis.comments.service.CommentService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentsApiController implements CommentsApi {

    private static final Logger logger = LoggerFactory.getLogger(CommentsApiController.class);

    private final CommentService commentService;

    @Autowired
    public CommentsApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentInput input) {
        logger.info("Received request to create comment with input: {}", input);
        Comment createdComment = commentService.createComment(input);
        logger.info("Created comment: {}", createdComment);
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer commentId) {
        logger.info("Received request to get comment by id: {}", commentId);
        Optional<Comment> comment = commentService.getCommentById(commentId);
        if (comment.isPresent()) {
            logger.info("Found comment: {}", comment.get());
            return ResponseEntity.ok(comment.get());
        } else {
            logger.warn("Comment with id {} not found", commentId);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId) {
        logger.info("Received request to delete comment with id: {}", commentId);
        commentService.deleteComment(commentId);
        logger.info("Deleted comment with id: {}", commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<Comment>> getCommentsByResource(@PathVariable Integer resourceId) {
        logger.info("Received request to get comments by resource id: {}", resourceId);
        List<Comment> comments = commentService.getCommentsByResource(resourceId);
        logger.info("Found {} comments for resource id {}", comments.size(), resourceId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable Integer userId) {
        logger.info("Received request to get comments by user id: {}", userId);
        List<Comment> comments = commentService.getCommentsByUser(userId);
        logger.info("Found {} comments for user id {}", comments.size(), userId);
        return ResponseEntity.ok(comments);
    }
}
