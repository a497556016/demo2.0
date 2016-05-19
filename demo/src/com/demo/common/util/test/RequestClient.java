package com.demo.common.util.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import com.demo.common.util.HttpClient;

public class RequestClient {
	public static void main(String[] args) {
		Map<String, String> params = new HashMap<>();
		params.put("userid", "31415");
		params.put("linkpage", "");
		params.put("userName", "31415");
		params.put("j_username", "31415");
		params.put("password", "8631");
		params.put("j_password", "8631");
		Map<String, String> header = new HashMap<>();
		header.put("Cookie", "JSESSIONID=40053530F587E687F515670B5B430857; ROLTPAToken=PExUUEFUb2tlbj48bm9kZT5SMUZyYW1ld29yazQuMDwvbm9kZT48dGltZT4xNDM4MzA4MDExNzI2PC90aW1lPjx1c2VyaWQ%2BMzE0MTU8L3VzZXJpZD48cGVyc29udXVpZD40MDI4OWY5OTQ2NjJiYTFjMDE0NjYyYmExZjY0MDAwNjwvcGVyc29udXVpZD48c3lzaWQ%2BMjwvc3lzaWQ%2BPC9MVFBBVG9rZW4%2B");
		String responseText = HttpClient.post("http://ics.chinasoftosg.com/login", params,header);
		System.out.println(responseText);
		responseText = HttpClient.get("http://sse.chinasoftosg.com/wf/gettodoTaskCount.do");
		System.out.println(responseText);
	}
}
