package com.pickshell.jplook.maintain;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.pickshell.jplook.dao.WordDao;
import com.pickshell.jplook.dao.WordDaoImpl;
import com.pickshell.jplook.domain.Word;
import com.pickshell.jplook.service.ParseWord;

public class DataManager {

	private WordDao wordDao = new WordDaoImpl();
	ParseWord parse = new ParseWord();

	@Test
	public void savaWordFromFile() throws IOException {
		File file = new File("D:/test/word.txt");
		List<String> list = FileUtils.readLines(file);
		int i = 0;
		for (String wordName : list) {
			try {
				List<Word> wordList = parse.lookWord(wordName);
				if (wordList == null) {
					continue;
				}
				for (Word word : wordList) {
					wordDao.save(word);
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				i++;
				if (i % 50 == 0) {
					System.out.println(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(wordName);
			}
		}
		System.out.println(i);

	}
}
