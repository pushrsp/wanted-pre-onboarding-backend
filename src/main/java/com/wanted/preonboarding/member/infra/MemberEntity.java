package com.wanted.preonboarding.member.infra;

import com.wanted.preonboarding.article.infra.ArticleEntity;
import com.wanted.preonboarding.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", length = 70, nullable = false)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    private List<ArticleEntity> articles = new ArrayList<>();

    @Builder
    private MemberEntity(Long id, String email, String password, LocalDateTime createdTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdTime = createdTime;
    }

    public static MemberEntity from(Member member) {
        MemberEntity memberEntity = MemberEntity.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .createdTime(member.getCreatedTime())
                .build();

        memberEntity.setId(member.getId());

        return memberEntity;
    }

    private void setId(String id) {
        if(id != null) {
            this.id = Long.parseLong(id);
        }
    }

    public void addArticle(ArticleEntity article) {
        this.articles.add(article);
    }

    public Member toDomain() {
        return Member.builder()
                .id(this.id.toString())
                .email(this.email)
                .password(this.password)
                .createdTime(this.createdTime)
                .build();
    }
}
