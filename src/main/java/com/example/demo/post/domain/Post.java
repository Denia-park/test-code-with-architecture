package com.example.demo.post.domain;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {
    private final Long id;
    private final String content;
    private final Long createdAt;
    private final Long modifiedAt;
    private final User writer;

    @Builder
    public Post(final Long id, final String content, final Long createdAt, final Long modifiedAt, final User writer) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writer = writer;
    }

    public static Post from(final User writer, final PostCreate postCreate, final ClockHolder clockHolder) {
        return Post.builder()
                .content(postCreate.getContent())
                .writer(writer)
                .createdAt(clockHolder.millis())
                .build();
    }

    public Post update(final PostUpdate postUpdate, final ClockHolder clockHolder) {
        return Post.builder()
                .id(id)
                .content(postUpdate.getContent())
                .createdAt(createdAt)
                .modifiedAt(clockHolder.millis())
                .writer(writer)
                .build();
    }

//    public Post update(PostUpdate postUpdate, ClockHolder clockHolder) {
//        return Post.builder()
//                .id(id)
//                .content(postUpdate.getContent())
//                .createdAt(createdAt)
//                .modifiedAt(clockHolder.millis())
//                .writer(writer)
//                .build();
//    }
}
