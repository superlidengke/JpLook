package com.pickshell.jplook.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pickshell.jplook.dao.WordDao;
import com.pickshell.jplook.dao.WordDaoImpl;
import com.pickshell.jplook.domain.Word;

public class ParseWordTest {
	WordDao wordDao;
	ParseWord parseWord;

	@Before
	public void setUp() throws Exception {
		wordDao = new WordDaoImpl();
		parseWord = new ParseWord();
	}

	@After
	public void tearDown() throws Exception {
		wordDao = null;
		parseWord = null;
	}

	@Test
	public void test() throws IOException {
		String path = System.getProperty("user.dir")
				+ "/src/test/java/com/pickshell/jplook/service/wordMeaning.html";
		File file = new File(path);
		String content = FileUtils.readFileToString(file);
		List<Word> list = parseWord.ajaxParse(content);
		for (Word word : list) {
			System.out.println(word);
			boolean re = wordDao.save(word);
			System.out.println("save:" + re);
		}

	}

}
