package com.github.project1.service;

import com.github.project1.Entity.post.PostEntity;
import com.github.project1.Entity.user.UserEntity;
import com.github.project1.dto.post.post;
import com.github.project1.dto.post.postBody;
import com.github.project1.repository.post.PostRepository;
import com.github.project1.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class PostService {


    private final  PostRepository postRepository;
    private final   UserRepository userRepository;

    public post createPost(postBody postBody) {
        Optional<UserEntity> optionalUser = Optional.ofNullable(userRepository.findByEmail(postBody.getAuthor()));
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }

        UserEntity user = optionalUser.get();
        PostEntity postEntity = PostEntity.builder()
                .title(postBody.getTitle())
                .content(postBody.getContent())
                .user(user)
                .build();

        postEntity = postRepository.save(postEntity);
        UserEntity userentity = optionalUser.get();
        return convertToDto(postEntity);
    }

    public List<post> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<post> getPostsByEmail(String email) {
        List<PostEntity> postEntities = postRepository.findByUserEmail(email);
        return postEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public post updatePost(Integer id, postBody postBody) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(id);
        if (optionalPostEntity.isPresent()) {
            PostEntity postEntity = optionalPostEntity.get();
            postEntity.setTitle(postBody.getTitle());
            postEntity.setContent(postBody.getContent());
            postRepository.save(postEntity);
            return convertToDto(postEntity);
        }
        return null;
    }

    public boolean deletePost(Integer id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private post convertToDto(PostEntity postEntity) {
        post post = new post();
        post.setId(postEntity.getId());
        post.setTitle(postEntity.getTitle());
        post.setContent(postEntity.getContent());
        post.setAuthor(postEntity.getUser().getEmail());
        post.setCreatedAt(postEntity.getCreatedAt());
        return post;
    }
}
