package com.store.retail.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.store.retail.model.Product;
import com.store.retail.model.User;

@Service
public interface StoreService {
	public double getNetPayment(User user, List<Product> products);
}