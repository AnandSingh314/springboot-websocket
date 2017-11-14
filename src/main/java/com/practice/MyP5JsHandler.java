package com.practice;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.mockito.internal.stubbing.ConsecutiveStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.GsonBuilder;
import com.practice.beans.Cordinates;
import com.practice.beans.P5JsRequestMessage;

/**
 * 
 * connect with dwst app: /connect ws://localhost:8080/p5js
 */
@Component
public class MyP5JsHandler extends TextWebSocketHandler {

	private ApplicationContext context;

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws IOException, InterruptedException {
		System.out.println("something came on p5js");
		System.out.println("Client: " + message.getPayload());
		try {
			processMessage(session, message.getPayload());
		} catch (AWTException e) {
			//e.printStackTrace();
			System.err.println("Error: "+message.getPayload());
		}
	}

	private void processMessage(WebSocketSession session, String payload) throws IOException, AWTException {

		P5JsRequestMessage request = new GsonBuilder().create().fromJson(payload, P5JsRequestMessage.class);
		Robot robot = new Robot();
		Cordinates cordinates = request.getBody();
		System.out.println(request);
		switch (request.getOperation()) {
		case "getResolution":
			List<GraphicsDevice> devices = (List<GraphicsDevice>) context.getBean("deviceList");
			cordinates = new Cordinates();
			cordinates.setX(devices.get(0).getDisplayMode().getWidth());
			cordinates.setY(devices.get(0).getDisplayMode().getHeight());
			TextMessage msg = new TextMessage(cordinates.toString());
			session.sendMessage(msg);
			break;
		case "move":
			System.err.println(cordinates);
			robot.mouseMove(cordinates.getX(),cordinates.getY());
			break;
		case "click":
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			break;
		case "dblclick":
			break;
		default:
			break;
		}

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

	/**
	 * @param context
	 *            the context to set
	 */
	@Autowired
	public void setContext(ApplicationContext context) {
		this.context = context;
	}

}