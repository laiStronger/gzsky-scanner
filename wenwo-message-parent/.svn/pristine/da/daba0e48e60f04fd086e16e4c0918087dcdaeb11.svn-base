/**
 * 
 */
package com.wenwo.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author shuangtai
 *
 */
public class GsonTest {

	
	public void testGSON(){
		String json = "{\"back_pic_id\":\"839415807_540.jpg\",\"text_info\":[{\"text\":\"fdsa\",\"x\":\"sdf\",\"y\":\"sdaf\",\"font\":\"sadf\",\"size\":\"sdaf\",\"color\":\"sdaf\"}],\"pic_info\":[{\"url\":\"dsfa\",\"x\":\"asdf\",\"y\":\"dasf\",\"defaultUrl\":\"asdf\",\"width\":\"sadf\",\"height\":\"sadf\"}],\"line_info\":{\"x\":\"sdf\",\"y\":\"dsfa\",\"height\":\"sdaf\",\"width\":\"sdfa\",\"color\":\"asdf\"}}";
//		Gson gons = new Gson();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		element.getAsJsonObject();
	}
	
	public static void main(String[] args) {
		new GsonTest().testGSON();
	}
	
}
