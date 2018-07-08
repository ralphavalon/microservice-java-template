package com.example.template.helper;

import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonHelper {
	
	private static Gson gson = new Gson();
	
	private static JsonObject getJsonFileAsJsonObject(String file, String type) {
		BufferedReader br = new BufferedReader(new InputStreamReader(JsonHelper.class.getResourceAsStream ("/json/" + type + "/" + file)));
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(br).getAsJsonObject();
		assertNotNull(json);
		return json;
	}
	
	public static JsonObject getRequestFileAsJsonObject(String file) {
		return getJsonFileAsJsonObject(file, "request");
	}
	
	public static JsonObject getResponseFileAsJsonObject(String file) {
		return getJsonFileAsJsonObject(file, "response");
	}
	
	public static String getRequestFileAsString(String file) {
		return getJsonFileAsJsonObject(file, "request").toString();
	}
	
	public static String getResponseFileAsString(String file) {
		return getJsonFileAsJsonObject(file, "response").toString();
	}
	
	public static void assertKeysLength(String json, int length) {
		JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
		assertThat(jsonObject.keySet().size()).isEqualTo(length);
	}
	
	public static <T> T parse(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

}
