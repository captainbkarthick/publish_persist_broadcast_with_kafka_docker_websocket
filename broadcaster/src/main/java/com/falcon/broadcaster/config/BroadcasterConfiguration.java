package com.falcon.broadcaster.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class BroadcasterConfiguration implements WebSocketMessageBrokerConfigurer {

	@Value("${broadcaster.websocket.endpoint}")
	private String broadcasterEndpoint;

	@Value("${broadcaster.websocket.topic-destination-prefix}")
	private String topicDestinationPrefix;

	@Value("${broadcaster.websocket.app-destination-prefix}")
	private String appDestinationPrefix;

	@Override
	public void configureMessageBroker(MessageBrokerRegistry brokerRegistry) {
		brokerRegistry.enableSimpleBroker(topicDestinationPrefix);
		brokerRegistry.setApplicationDestinationPrefixes(appDestinationPrefix);
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry stompRegistry) {
		log.info(broadcasterEndpoint);
		stompRegistry.addEndpoint(broadcasterEndpoint).withSockJS();
	}
}
