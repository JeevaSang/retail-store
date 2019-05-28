package com.store.retail.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.store.retail.model.Product;
import com.store.retail.model.User;
import com.store.retail.service.StoreService;
import com.store.retail.util.CommonUtil;

/**
 * @author jeeva
 *
 */
@RestController
public class BillGeneratorController {

	@Autowired
	private StoreService billGeneratorService;	

	/**
	 * Generating bill based on user type
	 * @param userid
	 * @param products
	 * @return NetPayment as a double object
	 * @throws Exception 
	 */
	@PostMapping("/bill-generate/{userId}")
	public double generateBill(@RequestBody List<Product> products,@PathVariable String userId) throws Exception {
		List<User> users = CommonUtil.convertJsonToPOJO("input/User.json",User.class);
		Optional<User> returnUser = users.stream().filter(user -> user.getUserId().equals( userId)).findAny();
		if(returnUser.isPresent())
			return billGeneratorService.getNetPayment(returnUser.get(), products);
		else
			throw new Exception("User Not Found");
	}
	
}