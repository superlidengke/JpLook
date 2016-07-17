package com.pickshell.jplook.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WordServiceTest {
	WordService service;

	@Before
	public void setUp() throws Exception {
		service = new WordService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetHtmlMeaning() {
		String str = service.getHtmlMeaning("月");
		str = service.getHtmlMeaning("月");
		System.out.println(str);
	}

}
