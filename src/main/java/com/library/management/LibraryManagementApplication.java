package com.library.management;

import ch.qos.logback.classic.Level;
import com.library.management.entity.Book;
import com.library.management.entity.User;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.service.BookService;
import com.library.management.service.UserService;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
public class LibraryManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService userService, BookService bookService, RestTemplate restTemplate) {
        return args -> {

            Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.ERROR);

            System.out.print("\033[H\033[2J");
            System.out.flush();

            seedUsers(userService);
            seedBooks(bookService);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter your username:");
            String username = scanner.next();

            long userId = 0;

            try {
                userId = userService.getUser(username).getId();
            } catch (ResourceNotFoundException ex) {
                System.out.println("User doesn't exists in the system!");
            }

            String command = "";

            while (!command.equals("4")) {
                System.out.println("Please select a command: 1. View 2. Borrow 3. Return 4. Exit (1/2/3/4)");
                command = scanner.next();
                switch (command) {
                    case "1" -> {
                        Arrays.stream(Objects.requireNonNull(restTemplate.getForObject("http://localhost:8080/api/books", Book[].class))).forEach(System.out::println);
                    }
                    case "2" -> {
                        System.out.println("Please enter a book id");
                        String bookId = scanner.next();
                        try {
                            ResponseEntity response = restTemplate.postForEntity("http://localhost:8080/api/user/" + userId + "/borrow/" + bookId, null, Void.class);
                            if (response.getStatusCode().is2xxSuccessful()) {
                                System.out.println("Book borrowed successfully");
                            } else {
                                System.out.println("Failed to borrow a book");
                            }
                        } catch (HttpClientErrorException ex) {
                            System.out.println("Failed to borrow the book! You're not eligible to borrow!");
                        }
                    }
                    case "3" -> {
                        System.out.println("Please enter a book id");
                        String bookId = scanner.next();
                        try {
                            restTemplate.delete("http://localhost:8080/api/user/" + userId + "/return/" + bookId);
                            System.out.println("Book returned successfully!");
                        } catch (HttpClientErrorException ex) {
                            System.out.println("Failed to return the book! Invalid Request");
                        }
                    }
                }
            }

            System.exit(0);
        };
    }

    private void seedUsers(UserService userService) {
        userService.createUser(new User(1, "john"));
        userService.createUser(new User(2, "kite"));
        userService.createUser(new User(3, "laura"));
        userService.createUser(new User(4, "hrishikesh"));
        userService.createUser(new User(5, "anthony"));
    }

    private void seedBooks(BookService bookService) {
        bookService.saveBook(new Book(1, "Harry Porter", "JK Rowling", "Fiction", 2));
        bookService.saveBook(new Book(2, "Lord of the Rings", "Tolkien", "Fiction", 2));
        bookService.saveBook(new Book(3, "Think and grow rich", "Napoleon", "Self-Help", 2));
        bookService.saveBook(new Book(4, "Thinking fast and slow", "Daniel Kanheman", "Non-Fiction", 1));
    }
}
