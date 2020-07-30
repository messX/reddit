package com.example.messx.redit.service;

import com.example.messx.redit.dto.PostRequest;
import com.example.messx.redit.dto.PostResponse;
import com.example.messx.redit.exception.PostNotFoundException;
import com.example.messx.redit.exception.SubredditNotFoundException;
import com.example.messx.redit.exception.UsernameNotFoundException;
import com.example.messx.redit.mapper.PostMapper;
import com.example.messx.redit.model.Post;
import com.example.messx.redit.model.Subreddit;
import com.example.messx.redit.model.User;
import com.example.messx.redit.repository.PostRepository;
import com.example.messx.redit.repository.SubredditRepository;
import com.example.messx.redit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class PostService {

    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse create(PostRequest postRequest){
        log.info(postRequest.getSubredditName());
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> {throw new SubredditNotFoundException(postRequest.getSubredditName());});
        Post post = postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
        return postMapper.mapToDto(post);
    }

    public List<PostResponse> getAll() {
        return postRepository.findAll().stream().map(postMapper::mapToDto).collect(toList());
    }

    public PostResponse getById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> {return new PostNotFoundException("Invalid Postid");});
        return postMapper.mapToDto(post);
    }

    @Transactional
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
