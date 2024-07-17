package com.github.project1.service;

import com.github.project1.Entity.comment.CommentEntity;
import com.github.project1.Entity.post.PostEntity;
import com.github.project1.repository.comment.CommentRepository;
import com.github.project1.repository.post.PostRepository;
import com.github.project1.dto.comment.CommentDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CommentService {


    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public List<CommentDTO> findByPostId(Integer postId) {
        return commentRepository.findAllByPost_Id(postId)
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public void saveComment(CommentDTO commentDto) {

        PostEntity postEntity = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));

        CommentEntity commentEntity = CommentEntity.builder()
                .post(postEntity)
                .author(commentDto.getAuthor())
                .content(commentDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        commentRepository.save(commentEntity);
    }

    public void updateCommentContent(Integer id, String content) {

            Optional<CommentEntity> existingComment = commentRepository.findById(id);
            if (existingComment.isPresent()) {
                CommentEntity commentEntity = existingComment.get();
                commentEntity.setContent(content);
                commentRepository.save(commentEntity);
            } else {
                throw new EntityNotFoundException("댓글을 찾을 수 없습니다.");
            }
    }


    public void deleteComment(Integer id) {
        Optional<CommentEntity> existingComment = commentRepository.findById(id);
        if (existingComment.isPresent()) {
            commentRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("댓글을 찾을 수 없습니다.");
        }
    }

    public String getCommentAuthor(Integer id) {
        return commentRepository.findById(id)
                .map(CommentEntity::getAuthor)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));
    }


    private CommentDTO convertEntityToDTO(CommentEntity comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getAuthor(),
                comment.getCreatedAt()
        );
    }
}
