package com.practice;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * 
 * https://stackoverflow.com/questions/32267015/spring-4-websocket-without-stomp-socketjs
 * https://stackoverflow.com/questions/27158106/websocket-with-sockjs-spring-4-but-without-stomp
 * */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	private ApplicationContext appContext;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myHandler(), "/greeting").setAllowedOrigins("*");
		registry.addHandler(myP5JsHandler(), "/p5js").setAllowedOrigins("*");
		//Arrays.stream(appContext.getBeanDefinitionNames()).forEach(System.out::println);
	}

	@Bean
	public WebSocketHandler myHandler() {
		System.out.println("my handler bean");
		return new MyHandler();
	}

	@Bean
	public WebSocketHandler myP5JsHandler() {
		System.out.println("my p5 js handler bean");
		return new MyP5JsHandler();
	}


	/**
	 * @param appContext the appContext to set
	 */
	@Autowired
	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}
	
}