package com.pickshell.jplook.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pickshell.jplook.service.WordService;

/**
 * Servlet implementation class AjaxWordLookup
 */
public class AjaxWordLookup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	WordService wordService = new WordService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjaxWordLookup() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		String wordName = request.getParameter("wordName");
		PrintWriter out = response.getWriter();
		// String result = wordService.getJsonMeaning(wordName);
		String result = wordService.getHtmlMeaning(wordName);
		// result =
		// "HJ.fun.jsonCallBack({content:\"単位\",IfHasScb:\"False\",hjd_langs:\"jc\"});";
		out.print(result);
		out.flush();
		out.close();
	}

}
