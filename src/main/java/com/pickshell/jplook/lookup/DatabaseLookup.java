package com.pickshell.jplook.lookup;

import java.util.List;

import com.pickshell.jplook.dao.WordDao;
import com.pickshell.jplook.dao.WordDaoImpl;
import com.pickshell.jplook.domain.Word;

public class DatabaseLookup implements LookUpWord {
	private WordDao wordDao = new WordDaoImpl();

	public List<Word> queryWord(String name) {
		return wordDao.getWord(name);
	}

	public List<String> getSimilarWords(String name, int limit) {
		return wordDao.fetchSimilarWords(name, limit);
	}

	public void save(Word word) {
		if (word != null) {
			wordDao.save(word);
		}
	}

	public long getTotalNumber() {
		return wordDao.getTotalNumber();
	}
}
