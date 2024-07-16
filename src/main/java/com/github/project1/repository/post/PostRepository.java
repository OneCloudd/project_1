package com.github.project1.repository.post;

import com.github.project1.web.dto.Post;
import com.github.project1.web.dto.PostBody;

import java.util.List;

public interface PostRepository {

     List<Post> findAllPost();


    List<Post> findPostByEmail(String email);

    void createPost(PostBody postBody);

    void deletePost(String title);

    PostEntity updatePost(PostBody postBody, Integer id);
}
