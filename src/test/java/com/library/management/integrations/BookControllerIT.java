package com.library.management.integrations;

import com.library.management.LibraryManagementApplication;
import com.library.management.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@SpringBootTest(classes = LibraryManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookControllerIT {
    @LocalServerPort
    int port;

    @Autowired
    RestTemplate restTemplate;

    @Test
    @Sql("/test_data.sql")
    @Sql(value = "/test_delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetAllBooks() {
        ResponseEntity<Book[]> response = restTemplate.getForEntity("http://localhost:" + port + "/api/books", Book[].class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(4, Objects.requireNonNull(response.getBody()).length);
    }
}
