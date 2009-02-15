package com.lesshassles.web;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class SessionControllerTest {
	
	@Test
	public void shouldShowLoginPage() {
		SessionController controller = new SessionController();
		
		String view = controller.showLogin();
		
		assertEquals("sessionNew", view);
	}
}
