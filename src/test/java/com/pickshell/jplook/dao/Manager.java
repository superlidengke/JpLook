package com.pickshell.jplook.dao;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.pickshell.jplook.util.UnicodeUtils;

import flexjson.JSONSerializer;

public class Manager {

	@Test
	public void decodeResp() throws IOException {
		File file = new File(System.getProperty("user.dir")
				+ "/src/test/resources/reponse.txt");
		String re = UnicodeUtils.decode(FileUtils.readFileToString(file));
		System.out.println(re);
	}

	@Test
	public void temp() {
		String html = "<ba/>";
		JSONSerializer serializer = new JSONSerializer();
		System.out.println(serializer.serialize(html));
	}
}
