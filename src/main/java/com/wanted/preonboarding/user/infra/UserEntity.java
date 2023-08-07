package com.wanted.preonboarding.user.infra;

import com.wanted.preonboarding.article.infra.ArticleEntity;
import com.wanted.preonboarding.user.domain.User;
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
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", length = 70, nullable = false)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<ArticleEntity> articles = new ArrayList<>();

    @Builder
    private UserEntity(Long id, String email, String password, LocalDateTime createdTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdTime = createdTime;
    }

    public static UserEntity from(User user) {
        return UserEntity.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .createdTime(user.getCreatedTime())
                .build();
    }

    public User toDomain() {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .createdTime(this.createdTime)
                .build();
    }
}
