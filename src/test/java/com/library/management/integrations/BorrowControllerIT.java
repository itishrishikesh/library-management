package com.library.management.integrations;

import com.library.management.LibraryManagementApplication;
import com.library.management.entity.Book;
import com.library.management.entity.Borrow;
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
public class BorrowControllerIT {
    @LocalServerPort
    int port;

    @Autowired
    RestTemplate restTemplate;

    @Test
    @Sql("/test_data.sql")
    @Sql(value = "/test_delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testBorrowingABook() {
        ResponseEntity<Borrow> response = restTemplate.postForEntity("http://localhost:" + port + "/user/1/borrow/1", null, Borrow.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @Sql("/test_data.sql")
    @Sql(value = "/test_delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testReturnABook() {
        ResponseEntity<Borrow> response = restTemplate.postForEntity("http://localhost:" + port + "/api/user/1/borrow/1", null, Borrow.class);
        restTemplate.delete("http://localhost:" + port + "/api/user/1/return/1");
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}
