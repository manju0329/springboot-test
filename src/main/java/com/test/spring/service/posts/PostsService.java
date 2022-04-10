package com.test.spring.service.posts;

import com.test.spring.domain.posts.Posts;
import com.test.spring.domain.posts.PostsRepository;
import com.test.spring.web.dto.PostsResponseDto;
import com.test.spring.web.dto.PostsSaveRequestDto;
import com.test.spring.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // final이 선언된 모든 필드를 인자값으로 하는 생성자를 만들어줌
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts =  postsRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        return new PostsResponseDto(entity);
    }
}
