package com.wanted.preonboarding.content.infra;

import com.wanted.preonboarding.content.domain.Content;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ContentEntityTest {
    @DisplayName("ContentEntity는 ContentDomain으로 부 생성할 수 있다.")
    @Test
    public void can_create_content_entity_from_content_domain() throws Exception {
        //given
        Content contentDomain = createContentDomain(null, "content");

        //when
        ContentEntity contentEntity = ContentEntity.from(contentDomain);

        //then
        assertThat(contentEntity.getId()).isNull();;
        assertThat(contentEntity.getContent()).isEqualTo(contentDomain.getContent());
    }

    @DisplayName("ContentEntity는 ContentDomain으로 컨버팅 할 수 있다.")
    @Test
    public void content_entity_can_convert_to_content_domain() throws Exception {
        //given
        ContentEntity contentEntity = ContentEntity.from(createContentDomain("20", "content"));

        //when
        Content contentDomain = contentEntity.toDomain();

        //then
        assertThat(contentDomain.getId()).isEqualTo(String.valueOf(contentEntity.getId()));
        assertThat(contentDomain.getContent()).isEqualTo(contentEntity.getContent());
    }

    private Content createContentDomain(String id, String content) {
        return Content.builder()
                .id(id)
                .content(content)
                .build();
    }
}
