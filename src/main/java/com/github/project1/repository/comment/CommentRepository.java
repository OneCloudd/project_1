package com.github.project1.repository.comment;

import com.github.project1.Entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    public List<CommentEntity>  findAllBy();

    List<CommentEntity> findAllByPost_Id(Integer postId);
}
