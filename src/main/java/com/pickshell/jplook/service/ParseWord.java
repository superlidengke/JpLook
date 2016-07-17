package com.pickshell.jplook.service;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.pickshell.jplook.domain.Word;
import com.pickshell.jplook.util.StringUtil;
import com.pickshell.jplook.util.UnicodeUtils;

public class ParseWord {

	/**
	 * 返回查到的单词列表， 没有查到这个词时，返回null
	 */
	public List<Word> lookWord(String name) throws IOException {
		// 返回空集合
		if (StringUtil.isBlank(name)) {
			return new ArrayList<Word>();
		}
		name = URLEncoder.encode(name, "UTF-8");
		String url = "http://dict.hjenglish.com/services/simpleExplain/jp_simpleExplain.ashx?type=jc&w="
				+ name;
		URL realUrl = new URL(url);
		// 打开和URL之间的连接
		URLConnection connection = realUrl.openConnection();
		// 设置通用的请求属性
		connection.setRequestProperty("accept", "*/*");

		// 建立实际的连接
		connection.connect();
		String result = IOUtils.toString(connection.getInputStream(), "UTF-8");
		result = UnicodeUtils.decode(result);
		return ajaxParse(result);
	}

	@Test
	public void lookupTest() throws IOException {
		lookWord("元数据");
	}

	List<Word> ajaxParse(String content) throws IOException {
		// 判断单词是否存在
		if (content.contains("id='hjd_nodata_msg'")) {
			return null;
		}
		String begin = "{content:\"";
		String end = "\",IfHasScb:";
		content = content.substring(content.indexOf(begin) + begin.length(),
				content.indexOf(end));
		content = content.replace("\\\"", "\"");
		List<Word> list = new ArrayList<Word>();

		String sep = "<span class='hjd_Green'";
		int bposition = content.lastIndexOf(sep);
		while (bposition != -1) {
			String htmlM = content.substring(bposition);
			list.add(parseWord(htmlM));
			content = content.substring(0, bposition);
			bposition = content.lastIndexOf(sep);
		}
		return list;
	}

	private Word parseWord(String html) {
		Word word = new Word();
		Document doc = Jsoup.parseBodyFragment(html);
		// 删除添加到我的单词本图片
		Elements eles = doc.select("span.hjd_add_myword");
		for (Element element : eles) {
			element.remove();
		}

		// 单词
		eles = doc.select("span font");
		word.setName(eles.first().text());
		// 罗马音
		eles = doc.select("span[title=假名]");
		if (eles != null && eles.size() > 0) {
			word.setRoman(eles.first().text().replaceAll("\\[|\\]", ""));
		}
		// 假名
		eles = doc.select("span[title=罗马音]");
		if (eles != null && eles.size() > 0) {
			word.setPronounce(eles.first().text().replaceAll("\\[|\\]", ""));
		}
		// 解释
		Element ele = doc.getElementsByAttributeValueStarting("id",
				"hjd_wordcomment_").first();
		String meaning = ele.attr("value");
		word.setMeaning(meaning);
		ele.remove();
		word.setHtmlMean(doc.getElementsByTag("body").html().replace("\n", ""));
		return word;
	}
}
