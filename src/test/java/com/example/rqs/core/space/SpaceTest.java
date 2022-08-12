package com.example.rqs.core.space;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("스페이스 테스트")
public class SpaceTest {

    @Test
    @DisplayName("타이틀 업데이트 테스트")
    void updateTitle() {
        Space space = Space.newSpace("업데이트 전!", false);

        space.updateTitle("업데이트 성공!");

        assertThat(space.getTitle()).isEqualTo("업데이트 성공!");
    }
}
