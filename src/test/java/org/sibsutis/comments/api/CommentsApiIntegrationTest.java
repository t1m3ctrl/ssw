package org.sibsutis.comments.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sibsutis.comments.model.Comment;
import org.sibsutis.comments.model.CommentInput;
import org.sibsutis.comments.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.http.HttpStatus;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("comments")
class CommentsApiIntegrationTest {

    @LocalServerPort
    private int port;  // Порт, на котором работает сервер

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @Container
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>("postgres:15");

    private CommentInput input;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }

    @BeforeAll
    static void startContainer() {
        POSTGRES_CONTAINER.start();
    }

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();

        input = new CommentInput();
        input.setUserId(1);
        input.setResourceId(1);
        input.content("This is a test comment");
    }

    @Test
    public void testCreateComment() {
        ResponseEntity<Comment> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/comments", input, Comment.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("This is a test comment", response.getBody().getContent());
    }

    @Test
    public void testGetCommentById() {
        ResponseEntity<Comment> createResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/comments", input, Comment.class);

        Integer commentId = Objects.requireNonNull(createResponse.getBody()).getId();

        ResponseEntity<Comment> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/comments/" + commentId, Comment.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(commentId, getResponse.getBody().getId());
    }

    @Test
    public void testDeleteComment() {
        ResponseEntity<Comment> createResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/comments", input, Comment.class);

        Integer commentId = Objects.requireNonNull(createResponse.getBody()).getId();

        restTemplate.delete("http://localhost:" + port + "/comments/" + commentId);

        ResponseEntity<Comment> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/comments/" + commentId, Comment.class);

        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    @Test
    public void testGetCommentsByResource() {
        CommentInput input1 = new CommentInput();
        input1.setUserId(1);
        input1.setResourceId(1);
        input1.setContent("Comment for resource 1");

        CommentInput input2 = new CommentInput();
        input2.setUserId(2);
        input2.setResourceId(1);
        input2.setContent("Another comment for resource 1");

        restTemplate.postForEntity("http://localhost:" + port + "/comments", input1, Comment.class);
        restTemplate.postForEntity("http://localhost:" + port + "/comments", input2, Comment.class);

        ResponseEntity<Comment[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/comments/resource/1", Comment[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).length > 0);
    }
}