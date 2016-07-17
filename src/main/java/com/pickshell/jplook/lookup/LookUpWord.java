package com.pickshell.jplook.lookup;

import java.util.List;

import com.pickshell.jplook.domain.Word;

public interface LookUpWord {
	public List<Word> queryWord(String name);
}
