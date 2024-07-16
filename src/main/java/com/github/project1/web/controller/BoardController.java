package com.github.project1.web.controller;

import com.github.project1.repository.post.PostEntity;
import com.github.project1.repository.post.PostRepository;
import com.github.project1.web.dto.Post;
import com.github.project1.web.dto.PostBody;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BoardController {

    private final PostRepository postRepository;

    // 조회
    @GetMapping("/posts")
    public List<Post> findAllPost(){
        return postRepository.findAllPost();
    }

    // 작성자로 게시물 검색
    @GetMapping("/posts/search")
    public List<Post> findBoardByEmail(@RequestParam("email") String email){
        return postRepository.findPostByEmail(email);
    }

    // 게시물 작성
    @PostMapping("/posts")
    public String createPost(@RequestBody PostBody postBody){
        postRepository.createPost(postBody);
        return "게시물이 성공적으로 작성되었습니다.";
    }

//    // 게시물 삭제
    @DeleteMapping("/posts/{title}")
    public String deletePost(@PathVariable String title){
        postRepository.deletePost(title);


        return "title이 " + title + "인 게시물이 삭제되었습니다.";
    }

//    // 게시물 수정
    @PutMapping("/posts/{id}")
    public PostEntity updatePost(@RequestBody PostBody postBody, @PathVariable Integer id){
        return postRepository.updatePost(postBody, id);
    }
}
