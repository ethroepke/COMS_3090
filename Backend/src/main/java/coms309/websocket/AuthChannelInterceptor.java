package coms309.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Component
@EnableWebSocketMessageBroker
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // Example: Check for a specific header or authentication token
        String authToken = accessor.getFirstNativeHeader("Authorization");

        // Validate the token or session for authentication
        if (authToken == null || !validateToken(authToken)) {
            throw new IllegalArgumentException("Unauthorized access");
        }

        return message;
    }

    private boolean validateToken(String token) {
        // Implement token validation logic
        return token.equals("Work@around123"); // Replace with real validation
    }
}