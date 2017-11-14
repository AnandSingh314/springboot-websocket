package com.practice;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebsocketDemoApplication {
	
	
	private static GraphicsDevice[] devices; 
	
/*	public static void main(String[] args) {
		setup();
		SpringApplication.run(WebsocketDemoApplication.class, args);
	}
*/
	public static void main(String[] args) {
		setup();
		SpringApplicationBuilder builder = new SpringApplicationBuilder(WebsocketDemoApplication.class);
		builder.bannerMode(Mode.OFF);
		builder.headless(true);
		builder.run(args);
	}
	private static List<GraphicsDevice> setup() {
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		devices = g.getScreenDevices();

		for (int i = 0; i < devices.length; i++) {
			System.out.println("Width:" + devices[i].getDisplayMode().getWidth());
			System.out.println("Height:" + devices[i].getDisplayMode().getHeight());
		}
		
		return Arrays.asList(devices);
	}
	
	@Bean
	public List<GraphicsDevice> deviceList(){
		return Arrays.asList(devices);
	}
}
