package com.test.spring.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    // db layer 접근자 <entity 클래스, pk 타입>

}
