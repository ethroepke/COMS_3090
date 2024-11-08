package coms309.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private ChannelInterceptor authChannelInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Set up a simple message broker on the "/topic" destination prefix
        config.enableSimpleBroker("/topic");

        // Set prefix for client messages to reach @MessageMapping endpoints
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register a WebSocket endpoint and allow cross-origin requests
        registry.addEndpoint("/task-updates")
                .setAllowedOrigins("*")
                .withSockJS()
                .setInterceptors(new HttpSessionHandshakeInterceptor());
        registry.addEndpoint("/notifications-websocket")
                .setAllowedOrigins("*")  // You can restrict allowed origins if needed for security
                .withSockJS()            // SockJS fallback
                .setInterceptors(new HttpSessionHandshakeInterceptor());
        registry.addEndpoint("/chat-socket")
                .setAllowedOrigins("*")
                .withSockJS()
                .setInterceptors(new HttpSessionHandshakeInterceptor());
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // Add an authentication interceptor to the inbound channel
        registration.interceptors(authChannelInterceptor);
    }


}