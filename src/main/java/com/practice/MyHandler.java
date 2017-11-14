package com.practice;

import static org.mockito.Matchers.shortThat;

import java.io.IOException;
import java.util.stream.IntStream;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 
 * connect with dwst app: /connect ws://localhost:8080/greeting
 * */
public class MyHandler extends TextWebSocketHandler {

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws IOException, InterruptedException {
		System.out.println("something came");
		Thread.sleep(3000); // simulated delay
		TextMessage msg = new TextMessage("Hello, " + message.getPayload() + "!");
		session.sendMessage(msg);

		IntStream.range(0, 100).filter(e -> ((int) e) % 2 == 0).forEach(i -> {
			try {
				Thread.sleep(1000);
				session.sendMessage(new TextMessage("Message " + i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#
	 * afterConnectionEstablished(org.springframework.web.socket.
	 * WebSocketSession)
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionEstablished(session);
		session.sendMessage(new TextMessage("Connected to the server."));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#
	 * afterConnectionClosed(org.springframework.web.socket.WebSocketSession,
	 * org.springframework.web.socket.CloseStatus)
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
		System.err.println("Conncetion closed from client.");
	}

}