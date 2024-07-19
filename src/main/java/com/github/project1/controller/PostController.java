package com.github.project1.controller;

import com.github.project1.dto.post.post;
import com.github.project1.dto.post.postBody;
import com.github.project1.dto.post.postResponse;
import com.github.project1.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody postBody postBody) {
         postService.createPost(postBody);
        return ResponseEntity.ok("게시글이 성공적으로 작성되었습니다.");
    }

    @GetMapping("/post/all")
    public postResponse getAllPosts() {
        List<post> posts = postService.getAllPosts();
        return new postResponse(posts);
    }

    @GetMapping("/post/{email}")
    public postResponse getPostsByEmail(@PathVariable String email) {
        List<post> posts = postService.getPostsByEmail(email);
        return new postResponse(posts);
    }

    @PutMapping("/post/{id}")
    public  ResponseEntity<String>  updatePost(@PathVariable Integer id, @RequestBody postBody postBody) {
        postService.updatePost(id, postBody);
        return ResponseEntity.ok("게시글이 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        boolean isDeleted = postService.deletePost(id);
        if (isDeleted) {
            return ResponseEntity.ok("게시글이 성공적으로 삭제 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");
        }
    }
}
