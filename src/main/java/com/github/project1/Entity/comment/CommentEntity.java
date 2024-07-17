package com.github.project1.Entity.comment;

import com.github.project1.Entity.post.PostEntity;

import com.github.project1.Entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@Builder
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;


    @Column(name = "content", length = 1000)
    private String content;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "author")
    private String author;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}

