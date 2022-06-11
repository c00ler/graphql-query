package com.github.avenderov;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
class QueryUsersTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldQueryAllUserIds() {
        final var userIds =
            this.graphQlTester
                .documentName("allUserIds")
                .execute()
                .path("users[*].id")
                .entityList(UUID.class)
                .get();

        assertThat(userIds).hasSize(10);
    }
}
