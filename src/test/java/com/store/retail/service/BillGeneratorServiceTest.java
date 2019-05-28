package com.store.retail.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.store.retail.model.Product;
import com.store.retail.model.User;
import com.store.retail.service.impl.BillGeneratorServiceImpl;

public class BillGeneratorServiceTest {

	@InjectMocks
	BillGeneratorServiceImpl billGeneratorServiceImpl;

	private static ObjectMapper objectMapper = new ObjectMapper();

	private static final double DELTA = 1e-15;

	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * If user is an employee will give 30% slot.
	 */
	@Test
	public void testProcess01() throws Exception{
		List<Product> products = convertJsonToPOJO("input/Product.json", Product.class);
		List<User> users = convertJsonToPOJO("input/User.json", User.class);
		double netPayment = billGeneratorServiceImpl.generateBill(users.get(0), products);
		Assert.assertEquals(28240.0, netPayment,DELTA);
	}
	
	/**
	 *  If user is an affiliate will give 10% slot.
	 */
	@Test
	public void testProcess02() throws Exception{
		List<Product> products = convertJsonToPOJO("input/Product.json", Product.class);
		List<User> users = convertJsonToPOJO("input/User.json", User.class);
		double netPayment = billGeneratorServiceImpl.generateBill(users.get(1), products);
		Assert.assertEquals(36240.0, netPayment,DELTA);
	}
	
	/**
	 *  If user is an affiliate will give 5% slot.
	 */
	@Test
	public void testProcess03() throws Exception{
		List<Product> products = convertJsonToPOJO("input/Product.json", Product.class);
		List<User> users = convertJsonToPOJO("input/User.json", User.class);
		double netPayment = billGeneratorServiceImpl.generateBill(users.get(2), products);
		Assert.assertEquals(38240.0, netPayment,DELTA);
	}
	
	/**
	 *  If 100rs on the bill,there would be a $5rs.
	 */
	@Test
	public void testProcess04() throws Exception{
		List<Product> products = convertJsonToPOJO("input/Product.json", Product.class);
		List<User> users = convertJsonToPOJO("input/User.json", User.class);
		double netPayment = billGeneratorServiceImpl.generateBill(users.get(3), products);
		Assert.assertEquals(38240.0, netPayment,DELTA);
	}
	
	@Test
	public void testProcess05() throws Exception{
		List<Product> products = new ArrayList<Product>();
		Product product = new Product();
		product.setProductId("PA005");
		product.setProductName("Dress");
		product.setProductType("Clothes");
		product.setQuantity(2);
		product.setProductAmount(40);
		products.add(product);
		List<User> users = convertJsonToPOJO("input/User.json", User.class);
		double netPayment = billGeneratorServiceImpl.generateBill(users.get(3), products);
		Assert.assertEquals(80.0, netPayment,DELTA);
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