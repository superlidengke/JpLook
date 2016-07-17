package com.pickshell.jplook.lookup;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pickshell.jplook.domain.Word;
import com.pickshell.jplook.service.ParseWord;

public class InternetLookup implements LookUpWord {
	private Logger logger = LoggerFactory.getLogger(InternetLookup.class);
	private ParseWord parser = new ParseWord();

	public List<Word> queryWord(String name) {
		List<Word> words = null;
		try {
			words = parser.lookWord(name);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return words;
	}
}
