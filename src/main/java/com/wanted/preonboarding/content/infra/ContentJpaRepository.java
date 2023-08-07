package com.wanted.preonboarding.content.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentJpaRepository extends JpaRepository<ContentEntity, Long> {
}
