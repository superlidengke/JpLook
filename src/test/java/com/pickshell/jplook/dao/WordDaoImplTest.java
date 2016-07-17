package com.pickshell.jplook.dao;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pickshell.jplook.domain.Word;

public class WordDaoImplTest {
	WordDao wordDao;

	@Before
	public void setUp() throws Exception {
		wordDao = new WordDaoImpl();
	}

	@After
	public void tearDown() throws Exception {
		wordDao = null;
	}

	@Test
	public void testSave() {
		Word word = new Word();
		word.setName("月");
		word.setMeaning("月分");
		wordDao.save(word);
	}

	@Test
	public void testIsExist() {
		Word word = new Word();
		word.setName("月");
		word.setMeaning("月分");
		boolean re = wordDao.isExist(word);
		System.out.println(re);
	}

	@Test
	public void testGetWordCache() {

		List<Word> words = wordDao.getWord("月");
		words = wordDao.getWord("月");
		for (Word word : words) {
			System.out.println(word);
		}
		// wordDao.getWordsById(18510);
		// wordDao.getWordsById(18510);
		//
		Word wordt = wordDao.get(18510);
		Word wordt2 = wordDao.get(18510);
		System.out.println(wordt + "," + (wordt == wordt2));
	}

}
