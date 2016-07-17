package com.pickshell.jplook.dao;

import java.util.List;

import com.pickshell.jplook.domain.Word;

public interface WordDao {
	public boolean save(Word word);

	public boolean update(Word word);

	public List<Word> fetchWords(int offset, int limit);

	public List<String> fetchSimilarWords(String name, int limit);

	public long getTotalNumber();

	public List<Word> getWord(String name);

	public List<Word> fetchWordsById(int minId, int maxId);

	public int getMaxId();

	public Word getWordsById(int id);

	public Word get(int id);

	public boolean isExist(Word word);

	public boolean deleteById(int id);
}
