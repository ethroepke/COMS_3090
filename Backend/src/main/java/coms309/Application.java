package coms309;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

/**
 * Main class for running the Spring Boot application.
 * This initializes the backend server and all required configurations.
 * 
 * <p>
 * Improvements:
 * - Added logging to track application startup.
 * - Enhanced exception handling to log potential startup failures.
 * </p>
 * 
 *
 */

@SpringBootApplication
@ComponentScan(basePackages = {"coms309.controller", "coms309.dto", "coms309.entity", "coms309.exception", "coms309.image", "coms309.repository", "coms309.service", "coms309.websocket"})
@EnableWebSocketMessageBroker
public class Application {
    // Create a logger for tracking the application lifecycle
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            logger.info("Starting the Backend Application...");
            SpringApplication.run(Application.class, args);
            logger.info("Backend Application started successfully.");
        } catch (Exception e) {
            logger.error("Failed to start the Backend Application: {}", e.getMessage(), e);
        }
    }
}