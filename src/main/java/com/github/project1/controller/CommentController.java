package com.github.project1.controller;

import com.github.project1.dto.comment.CommentDTO;
import com.github.project1.dto.comment.CommentResponse;
import com.github.project1.dto.comment.CommentUpdateDTO;
import com.github.project1.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments/all")
    public CommentResponse findAllComments() {
        List<CommentDTO> comments = commentService.findAll();
        return new CommentResponse(comments);
    }

    @GetMapping("/comments/{postId}")
    public CommentResponse findByPostId(@PathVariable Integer postId) {
        List<CommentDTO> comments = commentService.findByPostId(postId);
        return new CommentResponse(comments);
    }


    @PostMapping("/comments")
    public ResponseEntity<String> createComment(@RequestBody CommentDTO commentDto) {
        commentService.saveComment(commentDto);
        return ResponseEntity.ok("댓글이 성공적으로 작성되었습니다.");
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<String> updateComment(@PathVariable("id") Integer id, @RequestBody CommentUpdateDTO commentUpdateDTO) {

        commentService.updateCommentContent(id, commentUpdateDTO.getContent());
        return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }
}
