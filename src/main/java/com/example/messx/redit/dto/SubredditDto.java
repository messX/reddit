package com.example.messx.redit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
@Builder
public class SubredditDto {
    private Long id;
    private String subredditName;
    private String description;
    private Integer numberOfPosts;
}
