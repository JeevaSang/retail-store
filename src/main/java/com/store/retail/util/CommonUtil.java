package com.store.retail.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class CommonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * convertJsonToPOJO - JSON string to POJO Object
	 * @param <T>
	 * @param jsonUrl
	 * @param t
	 * @return List Object
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 */
	public static <T> List<T> convertJsonToPOJO(String jsonUrl, Class<T> t)
			throws IOException, URISyntaxException, JsonParseException, JsonMappingException {
		String jsonStr = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(jsonUrl).toURI())));
		CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, t);
		List<T> object = objectMapper.readValue(jsonStr, listType);
		return object;
	}
	
	/**
	 * Converting String object to LocalDateTime Object
	 * 
	 * @param dateStr
	 * @return LocalDateTime Object
	 */
	public static LocalDateTime convertStringToDateTime(String dateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(dateStr, formatter);
	}
}
