package org.sibsutis.comments.repository;

import org.sibsutis.comments.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findByResourceId(Integer resourceId);
    List<Comment> findByUserId(Integer userId);
}
