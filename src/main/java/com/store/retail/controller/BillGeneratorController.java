package com.store.retail.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.store.retail.model.Product;
import com.store.retail.model.User;
import com.store.retail.service.impl.BillGeneratorServiceImpl;

/**
 * @author jeeva
 *
 */
@RestController
public class BillGeneratorController {

	@Autowired
	private BillGeneratorServiceImpl billGeneratorServiceImpl;
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Generating bill based on user type
	 * @param userid
	 * @param products
	 * @return NetPayment as a double object
	 * @throws Exception 
	 */
	@PostMapping("/bill-generate/{userId}")
	public double generateBill(@RequestBody List<Product> products,@PathVariable String userId) throws Exception {
		List<User> users = convertJsonToPOJO("input/User.json",User.class);
		Optional<User> returnUser = users.stream().filter(user -> user.getUserId().equals( userId)).findAny();
		if(returnUser.isPresent())
			return billGeneratorServiceImpl.generateBill(returnUser.get(), products);
		else
			throw new Exception("User Not Found");
	}
	
	private static <T> List<T> convertJsonToPOJO(String jsonUrl, Class<T> t)
			throws IOException, URISyntaxException, JsonParseException, JsonMappingException {
		String jsonStr = new String (Files.readAllBytes(
				Paths.get(ClassLoader.getSystemResource(jsonUrl).toURI())));
	    CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, t);
		List<T> object = objectMapper.readValue(jsonStr, listType);
		return object;
	}
}