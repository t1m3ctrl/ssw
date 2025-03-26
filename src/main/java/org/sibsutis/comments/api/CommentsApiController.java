package org.sibsutis.comments.api;

import org.sibsutis.comments.model.Comment;
import org.sibsutis.comments.model.CommentInput;
import org.sibsutis.comments.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentsApiController implements CommentsApi {

    private final CommentService commentService;

    @Autowired
    public CommentsApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentInput input) {
        return ResponseEntity.ok(commentService.createComment(input));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer commentId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        return comment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<Comment>> getCommentsByResource(@PathVariable Integer resourceId) {
        return ResponseEntity.ok(commentService.getCommentsByResource(resourceId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(commentService.getCommentsByUser(userId));
    }
}
