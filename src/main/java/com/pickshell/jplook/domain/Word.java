package com.pickshell.jplook.domain;

public class Word {
	private int id;
	private String name;
	private String pronounce;
	private String roman;
	private String meaning;
	private String tone;// 音调
	private String htmlMean;

	@Override
	public String toString() {
		return "Word [id=" + id + ", pronounce=" + pronounce + ", roman="
				+ roman + ", name=" + name + ", meaning=" + meaning + ", tone="
				+ tone
		// + ", htmlMean=" + htmlMean + "]"
		;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPronounce() {
		return pronounce;
	}

	public void setPronounce(String pronounce) {
		this.pronounce = pronounce;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getTone() {
		return tone;
	}

	public void setTone(String tone) {
		this.tone = tone;
	}

	public String getHtmlMean() {
		return htmlMean;
	}

	public void setHtmlMean(String htmlMean) {
		this.htmlMean = htmlMean;
	}

	public String getRoman() {
		return roman;
	}

	public void setRoman(String roman) {
		this.roman = roman;
	}
}
