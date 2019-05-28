package com.store.retail.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.store.retail.model.Product;
import com.store.retail.model.User;
import com.store.retail.service.StoreService;
import com.store.retail.util.CommonUtil;

/**
 * @author jeeva
 *
 */
@Component("billGeneratorService")
public class BillGeneratorServiceImpl implements StoreService {

	private static Logger logger = LoggerFactory.getLogger(BillGeneratorServiceImpl.class);

	/**
	 * Generate the net payment based on the user 4 type discount logic 
	 * 1. If user is an employee will give 30% slot. 
	 * 2. If user is an affiliate will give 10% slot.
	 * 3. If user is exists over 2 years will give 5% slot. 
	 * 4. If 100rs on the bill,there would be a $5rs. 
	 * 5. Coupon won't application for Groceries.
	 * @param user
	 * @param products
	 * @return NetPayment as a double object
	 */
	@Override
	public double getNetPayment(User user, List<Product> products) {
		logger.info("Processing the discount based on user type");
		logger.info("User info :: " + user);
		logger.info("Product details :: " + products);

		double withoutGroceryAmt = products.stream().filter(product -> !product.getProductType().equals("Groceries"))
				.mapToDouble(product -> product.getProductAmount() * product.getQuantity()).sum();
		logger.info("Without Groceries total amount :: " + withoutGroceryAmt);

		double groceryAmtOnly = products.stream().filter(product -> product.getProductType().equals("Groceries"))
				.mapToDouble(product -> product.getProductAmount() * product.getQuantity()).sum();
		logger.info("Groceries total amount :: " + groceryAmtOnly);

		double netPayment = 0;

		if (user.getUserType().equals("Employee"))
			netPayment = withoutGroceryAmt - (withoutGroceryAmt * 0.3); // Step 1 - 30% slot
		else if (user.getUserType().equals("Affiliate"))
			netPayment = withoutGroceryAmt - (withoutGroceryAmt * 0.1); // Step 2 - 10% slot
		else if (CommonUtil.convertStringToDateTime((String) user.getCreateDate()).plusYears(2).isBefore(LocalDateTime.now()))
			netPayment = withoutGroceryAmt - (withoutGroceryAmt * 0.05); // Step 3 - 5% slot
		else if (withoutGroceryAmt >= 100)
			netPayment = withoutGroceryAmt - ((withoutGroceryAmt - (withoutGroceryAmt % 100)) * 0.05); // Step 4 - $5
		else
			netPayment = withoutGroceryAmt;

		logger.info("Total net amount without Groceries :: " + netPayment);

		netPayment = netPayment + groceryAmtOnly;

		logger.info("Total net amount  :: " + netPayment);
		return netPayment;
	}



}