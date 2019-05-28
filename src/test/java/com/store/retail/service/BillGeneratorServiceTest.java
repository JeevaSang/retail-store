package com.store.retail.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.store.retail.model.Product;
import com.store.retail.model.User;
import com.store.retail.service.impl.BillGeneratorServiceImpl;
import com.store.retail.util.CommonUtil;

public class BillGeneratorServiceTest {

	@InjectMocks
	BillGeneratorServiceImpl billGeneratorServiceImpl;

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
		List<Product> products = CommonUtil.convertJsonToPOJO("input/Product.json", Product.class);
		List<User> users = CommonUtil.convertJsonToPOJO("input/User.json", User.class);
		double netPayment = billGeneratorServiceImpl.getNetPayment(users.get(0), products);
		Assert.assertEquals(28240.0, netPayment,DELTA);
	}
	
	/**
	 *  If user is an affiliate will give 10% slot.
	 */
	@Test
	public void testProcess02() throws Exception{
		List<Product> products = CommonUtil.convertJsonToPOJO("input/Product.json", Product.class);
		List<User> users = CommonUtil.convertJsonToPOJO("input/User.json", User.class);
		double netPayment = billGeneratorServiceImpl.getNetPayment(users.get(1), products);
		Assert.assertEquals(36240.0, netPayment,DELTA);
	}
	
	/**
	 *  If user is an affiliate will give 5% slot.
	 */
	@Test
	public void testProcess03() throws Exception{
		List<Product> products = CommonUtil.convertJsonToPOJO("input/Product.json", Product.class);
		List<User> users = CommonUtil.convertJsonToPOJO("input/User.json", User.class);
		double netPayment = billGeneratorServiceImpl.getNetPayment(users.get(2), products);
		Assert.assertEquals(38240.0, netPayment,DELTA);
	}
	
	/**
	 *  If 100rs on the bill,there would be a $5rs.
	 */
	@Test
	public void testProcess04() throws Exception{
		List<Product> products = CommonUtil.convertJsonToPOJO("input/Product.json", Product.class);
		List<User> users = CommonUtil.convertJsonToPOJO("input/User.json", User.class);
		double netPayment = billGeneratorServiceImpl.getNetPayment(users.get(3), products);
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
		List<User> users = CommonUtil.convertJsonToPOJO("input/User.json", User.class);
		double netPayment = billGeneratorServiceImpl.getNetPayment(users.get(3), products);
		Assert.assertEquals(80.0, netPayment,DELTA);
	}

}