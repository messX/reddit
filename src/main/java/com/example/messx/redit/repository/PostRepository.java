package com.example.messx.redit.repository;

import com.example.messx.redit.model.Post;
import com.example.messx.redit.model.Subreddit;
import com.example.messx.redit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
