package com.pickshell.jplook.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pickshell.jplook.domain.Word;
import com.pickshell.jplook.lookup.DatabaseLookup;
import com.pickshell.jplook.lookup.InternetLookup;
import com.pickshell.jplook.lookup.LookUpWord;
import com.pickshell.jplook.util.Constants;
import com.pickshell.jplook.util.StringUtil;

import flexjson.JSONSerializer;

public class WordService {
	private static Logger logger = LoggerFactory.getLogger(WordService.class);
	private DatabaseLookup lookupDb = new DatabaseLookup();
	private LookUpWord internetLook = new InternetLookup();
	JSONSerializer serializer = new JSONSerializer();

	/**
	 * 从数据库或网上查询单词
	 * 
	 * @param name
	 * @return
	 */
	public List<Word> getWords(String name) {
		if (name == null || name.trim().equals("")) {
			return null;
		}
		name = name.trim();
		// 日语的空格
		name = name.replace(Constants.JP_SPACE, "");
		List<Word> words = lookupDb.queryWord(name);
		// 若数据库中不存在则从网上查询，并将结果保存到数据库
		if (words == null || words.size() == 0) {
			words = internetLook.queryWord(name);
			if (words != null) {
				for (Word word : words) {
					lookupDb.save(word);
				}
			}
		}
		return words;
	}

	/**
	 * 返回一个字符串，适合纯文本显示
	 * 
	 * @param name
	 * @return
	 */
	public String getMeaning(String name) {

		StringBuilder builder = new StringBuilder();
		List<Word> words = getWords(name);

		if (words != null && words.size() > 0) {
			for (Word word : words) {
				if (word == null) {
					continue;
				}
				builder.append("total:" + words.size()).append('\n');
				builder.append(word.getId()).append('\n');
				if (StringUtil.isNotBlank(word.getName())) {
					builder.append(word.getName()).append('\n');
				}
				if (StringUtil.isNotBlank(word.getTone())) {
					builder.append(word.getTone());
				}
				if (StringUtil.isNotBlank(word.getPronounce())) {
					builder.append(word.getPronounce()).append('\n');
				}
				if (StringUtil.isNotBlank(word.getMeaning())) {
					builder.append(word.getMeaning()).append('\n');
				}
			}
		} else {

			builder.append(name + Constants.WORD_UNFOUND);
		}
		return builder.toString();
	}

	public String getHtmlMeaning(String name) {
		StringBuilder builder = new StringBuilder();
		List<Word> words = getWords(name);
		if (words != null && words.size() > 0) {
			for (Word word : words) {
				if (word == null) {
					continue;
				}
				builder.append(word.getHtmlMean());
			}
		} else {
			builder.append(name + Constants.WORD_UNFOUND);
		}
		return builder.toString();
	}

	public String getJsonMeaning(String name) {
		String meaning = getHtmlMeaning(name);
		meaning = serializer.serialize(meaning);
		// result =
		// "HJ.fun.jsonCallBack({content:\"単位\",IfHasScb:\"False\",hjd_langs:\"jc\"});";
		StringBuilder builder = new StringBuilder();
		builder.append("HJ.fun.jsonCallBack({content:").append(meaning)
				.append(",IfHasScb:\"False\",hjd_langs:\"jc\"});");
		return builder.toString();
	}

	public List<String> getSimilarWords(String name) {
		if (StringUtil.isBlank(name)) {
			return new ArrayList<String>();
		}
		List<String> list = lookupDb.getSimilarWords(name, 20);
		logger.debug(list.toString());
		return list;
	}

	public long getTotalNumber() {
		return lookupDb.getTotalNumber();
	}

}
