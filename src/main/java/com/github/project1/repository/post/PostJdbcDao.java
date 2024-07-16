package com.github.project1.repository.post;

import com.github.project1.web.dto.Post;
import com.github.project1.web.dto.PostBody;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class PostJdbcDao implements PostRepository{

    private JdbcTemplate jdbcTemplate;

    static RowMapper<PostEntity> postEntityRowMapper = ((rs, rowNum) ->
            new PostEntity(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getNString("title"),
                    rs.getNString("author"),
                    rs.getDate("created_at"),
                    rs.getNString("content")
            ));

    @Override
    public List<Post> findAllPost() {
        List<PostEntity> postEntities = jdbcTemplate.query("SELECT * FROM post", postEntityRowMapper);
        return postEntities.stream().map(Post::new).collect(Collectors.toList());
    }

//    @Override
//    public List<Post> findPostByEmail(String email) {
//        List<PostEntity> postEntities = jdbcTemplate.query("SELECT * FROM post WHERE author = ?", postEntityRowMapper, author);
//        return postEntities.stream().map(Post::new).collect(Collectors.toList());
//    }
    @Override
    public List<Post> findPostByEmail(String email) {
        List<PostEntity> postEntities = jdbcTemplate.query("SELECT post.id, post.user_id, post.title, post.author, post.created_at, post.content FROM post LEFT JOIN users ON users.id=post.user_id WHERE users.email =?", postEntityRowMapper, email);
        return postEntities.stream().map(Post::new).collect(Collectors.toList());
    }

    @Override
    public void createPost(PostBody postBody) {
        jdbcTemplate.update("INSERT INTO post(title, content, author) VALUES (?, ?, ?)",
        postBody.getTitle(), postBody.getContent(), postBody.getAuthor());
    }

    @Override
    public void deletePost(String title) {
//        PostEntity findPostByTitle = jdbcTemplate.queryForObject("SELECT * FROM post WHERE = ?", postEntityRowMapper, title);
        jdbcTemplate.update("DELETE FROM post WHERE title = ?", title);

    }

    @Override
    public PostEntity updatePost(PostBody postBody, Integer id) {
        jdbcTemplate.update("UPDATE post SET title = ?, content = ? WHERE id = ?", postBody.getTitle(), postBody.getContent(), id);

        return jdbcTemplate.queryForObject("SELECT * FROM post WHERE id = ?", postEntityRowMapper, id);
    }
}

