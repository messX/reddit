package com.example.messx.redit.mapper;

import com.example.messx.redit.dto.SubredditDto;
import com.example.messx.redit.model.Post;
import com.example.messx.redit.model.Subreddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    @Mapping(source = "name", target = "subredditName")
    SubredditDto mapSubreddittoDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> posts){
        return posts.size();
    }
}
