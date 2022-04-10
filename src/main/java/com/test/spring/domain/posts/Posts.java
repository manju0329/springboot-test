package com.test.spring.domain.posts;

import com.test.spring.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity // 테이블과 링크될 클래스 -> setter 만들지 x -> builder 사용
public class Posts extends BaseTimeEntity {

    @Id // pk 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 생성 규칙
    private Long id;

    @Column(length = 500, nullable = false) //칼럼 설정 추가로 변경할때 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
