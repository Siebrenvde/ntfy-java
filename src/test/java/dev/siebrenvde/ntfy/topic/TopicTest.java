package dev.siebrenvde.ntfy.topic;

import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.time.Duration;

import static dev.siebrenvde.ntfy.topic.Topic.DEFAULT_HOST;
import static dev.siebrenvde.ntfy.topic.Topic.topic;
import static dev.siebrenvde.ntfy.topic.TopicImpl.DEFAULT_CLIENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TopicTest {

    private static final String HOST = "https://example.com";
    private static final String NAME = "name";
    private static final HttpClient CLIENT = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(12345)).build();
    private static final Duration TIMEOUT = Duration.ofSeconds(12345);

    private static final String TOKEN = "token";
    private static final String TOKEN_HEADER = "Bearer token";

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String BASIC_HEADER = "Basic dXNlcm5hbWU6cGFzc3dvcmQ="; // username:password -> base64

    private static final Topic TOPIC = new TopicImpl(HOST, NAME, CLIENT, TIMEOUT);
    private static final TopicImpl.Protected PROTECTED_TOPIC_TOKEN = new TopicImpl.Protected(DEFAULT_HOST, NAME, DEFAULT_CLIENT, null, TOKEN);
    private static final TopicImpl.Protected PROTECTED_TOPIC_BASIC = new TopicImpl.Protected(DEFAULT_HOST, NAME, DEFAULT_CLIENT, null, USERNAME, PASSWORD);

    @Test
    void testBuilder() {
        assertEquals(
            TOPIC,
            topic(NAME)
                .host(HOST)
                .httpClient(CLIENT)
                .timeout(TIMEOUT)
                .build()
        );
    }

    @Test
    void testInvalidHostThrows() {
        assertThrows(IllegalArgumentException.class, () -> topic(NAME).host("host").build());
    }

    @Test
    void testProtectedTokenBuilder() {
        assertEquals(
            PROTECTED_TOPIC_TOKEN,
            topic(NAME).token(TOKEN).build()
        );
    }

    @Test
    void testProtectedBasicBuilder() {
        assertEquals(
            PROTECTED_TOPIC_BASIC,
            topic(NAME)
                .username(USERNAME)
                .password(PASSWORD)
                .build()
        );
    }

    @Test
    void testProtectedTokenHeader() {
        assertEquals(TOKEN_HEADER, PROTECTED_TOPIC_TOKEN.header);
    }

    @Test
    void testProtectedBasicHeader() {
        assertEquals(BASIC_HEADER, PROTECTED_TOPIC_BASIC.header);
    }

    @Test
    void testBuilderTokenAndBasicThrows() {
        assertThrows(IllegalStateException.class, () -> {
            topic(NAME)
                .token(TOKEN)
                .username(USERNAME)
                .password(PASSWORD)
                .build();
        });
    }

    @Test
    void testBuilderUsernameWithoutPasswordThrows() {
        assertThrows(IllegalStateException.class, () -> topic(NAME).username(USERNAME).build());
    }

    @Test
    void testBuilderPasswordWithoutUsernameThrows() {
        assertThrows(IllegalStateException.class, () -> topic(NAME).password(PASSWORD).build());
    }

    @Test
    void testToBuilder() {
        assertEquals(TOPIC, TOPIC.toBuilder().build());
    }

    // TODO: Add createRequest tests

}
