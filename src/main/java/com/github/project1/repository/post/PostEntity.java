package com.github.project1.repository.post;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class PostEntity {
    private Integer id;
    private Integer userId;
    private String title;
    private String author;
    private Date createdAt;
    private String content;

    public PostEntity(Integer id, Integer userId, String title, String author, Date createdAt, String content) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.createdAt = createdAt;
        this.content = content;
    }

    // first commit
    // master protect test111111
}
